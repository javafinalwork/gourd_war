package cn.edu.nju.battle;

import cn.edu.nju.constant.Constant;
import cn.edu.nju.component.Bullet;
import cn.edu.nju.component.Creature;
import cn.edu.nju.component.Direction;
import cn.edu.nju.component.GridMap;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.HashMap;


public class Battlefield
{
    GridMap gridMap = new GridMap();
    final int GRID_NUM = gridMap.getGridNum();
    Creature[] calabashBrothers = new Creature[GRID_NUM];
    Creature[] monsters = new Creature[GRID_NUM];
    HashMap<String, Creature> creatureMap = new HashMap<>();
    ResourceManager resourceManager = new ResourceManager();
    Recorder recorder;

    final int INVALID_ID = -1;
    int moveId = INVALID_ID;

    boolean isServer;
    Pane pane;
    Battle battle;

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<ImageView> grayGrids = new ArrayList<>();

    Battlefield(boolean isServer, Pane pane, Battle battle, Recorder recorder)
    {
        this.isServer = isServer;
        this.pane = pane;
        this.battle = battle;
        this.recorder = recorder;
    }

    public void parseMsgOnPlayBack(BattleMsg msg)
    {
        if (msg.msgType == MsgType.MOVE_MSG)
        {
            if (msg.isFromServer())
            {
                moveCreature(msg.getSrcId(), msg.getDstId(), calabashBrothers);
            }
            else
            {
                moveCreature(msg.getSrcId(), msg.getDstId(), monsters);
            }
        }
        else if (msg.msgType == MsgType.DAMAGE_MSG)
        {
            attackCreature(msg.getName(), msg.getDamage());
        }
        else if (msg.msgType == MsgType.BULLET_MSG)
        {
            generateBullet(msg.getSrcId(), msg.getDstId(), msg.isFromServer());
        }
    }

    public void parseMsg(BattleMsg msg)
    {
        if (msg.msgType == MsgType.MOVE_MSG)
        {
            if (isServer)
            {
                if (!resourceManager.lockResource(msg))
                {
                    return;
                }
                else
                {
                    if (!msg.isFromServer())
                    {
                        battle.sendBack(msg);
                    }
                }
                recorder.writeToFile(msg);
            }
            if (msg.isFromServer())
            {
                moveCreature(msg.getSrcId(), msg.getDstId(), calabashBrothers);
            }
            else
            {
                moveCreature(msg.getSrcId(), msg.getDstId(), monsters);
            }
        }
        else if (msg.msgType == MsgType.DAMAGE_MSG)
        {
            if (isServer)
            {
                recorder.writeToFile(msg);
            }
            attackCreature(msg.getName(), msg.getDamage());
        }
        else if (msg.msgType == MsgType.BULLET_MSG)
        {
            if (isServer)
            {
                recorder.writeToFile(msg);
            }
            generateBullet(msg.getSrcId(), msg.getDstId(), msg.isFromServer());
        }
    }

    private void moveCreature(int id, int dstId, Creature[] creatures)
    {
        Creature creature = creatures[id];
        Point2D p = gridMap.gridIdToCreaturePos(dstId);
        Direction direction = gridMap.judgeDirection(id, dstId);
        creature.startMove(p.getX(), p.getY(), direction);
        creatures[dstId] = creature;
        creature.setGridId(dstId);
        creatures[id] = null;
    }

    private void attackCreature(String name, int damage)
    {
        creatureMap.get(name).setDamage(damage);
    }

    private void generateBullet(int id, int dstId, boolean isFromServer)
    {
        Creature cre;
        if (isFromServer)
        {
            cre = calabashBrothers[id];
        }
        else
        {
            cre = monsters[id];
        }
        if (cre == null)
        {
            return;
        }

        Direction direction = gridMap.judgeDirection(id, dstId);
        double dis = gridMap.getBulletRange(direction, cre.getAttackRange());
        Point2D p = gridMap.getBulletPos(id, dstId);
        Point2D pc=gridMap.gridIdToCreaturePos(dstId);
        Bullet bullet = new Bullet(cre.getBulletType(), direction, isFromServer,
                cre.getAttack(), p.getX(), p.getY(), pc.getX(), pc.getY(), dis);
        bullets.add(bullet);
        Platform.runLater(() -> pane.getChildren().add(bullet.getImgView()));
    }


    void setPosById(int id, Creature cre)
    {
        Point2D p = gridMap.gridIdToCreaturePos(id);
        cre.move((int) p.getX(), (int) p.getY());
    }

    public BattleMsg moveCreatureEvent(double x, double y, long clock)
    {
        int gridId = gridMap.posToGridId(x, y);
        BattleMsg msg = null;
        if (gridId == INVALID_ID)
        {
            moveId = INVALID_ID;
        }
        else
        {
            if (moveId != INVALID_ID)
            {
                if (isEmptyGrid(gridId) && gridMap.isGridInRange(moveId, gridId, 1))
                {
                    msg = new MoveMsg(moveId, gridId, isServer, clock);
                    if (isServer)
                    {
                        parseMsg(msg);
                    }
                }
                moveId = INVALID_ID;
            }
            else
            {
                if (isServer && calabashBrothers[gridId] != null && !calabashBrothers[gridId].isDied())
                {
                    moveId = gridId;
                }
                else if (!isServer && monsters[gridId] != null && !monsters[gridId].isDied())
                {
                    moveId = gridId;
                }
            }
        }
        if (moveId != INVALID_ID)
        {
            showMoveRange(moveId);
        }
        else
        {
            removeMoveRange();
        }
        return msg;
    }

    public void updateCreature()
    {
        for (Creature cre : creatureMap.values())
        {
            cre.update();
        }
    }


    private void showMoveRange(int centerId)
    {
        for (int id : gridMap.getNeighborGrid(centerId))
        {
            ImageView imageView = new ImageView(Constant.GRAY_GRID);
            Point2D p = gridMap.gridIdToPos(id);
            imageView.relocate(p.getX(), p.getY());
            grayGrids.add(imageView);
            pane.getChildren().add(imageView);
        }
    }

    private void removeMoveRange()
    {
        for (ImageView imgView : grayGrids)
        {
            pane.getChildren().remove(imgView);
        }
        grayGrids.clear();
    }


    private boolean isEmptyGrid(int gridId)
    {
        return calabashBrothers[gridId] == null && monsters[gridId] == null;
    }


    public ArrayList<BattleMsg> attackEvent(long clock)
    {
        ArrayList<BattleMsg> msgList = new ArrayList<>();
        if (isServer)
        {
            detectAttacks(msgList, calabashBrothers, monsters, clock);
        }
        else
        {
            detectAttacks(msgList, monsters, calabashBrothers, clock);
        }
        for (BattleMsg msg : msgList)
        {
            parseMsg(msg);
        }
        return msgList;
    }

    private void detectAttacks(ArrayList<BattleMsg> msgList, Creature[] creatures, Creature[] enemies, long clock)
    {
        for (Creature cre : creatures)
        {
            if (cre == null || cre.isDied() || !cre.isTriggered())
            {
                continue;
            }
            int enemyId = -1;
            for (Creature enemy : enemies)
            {
                if (enemy != null && !enemy.isDied())
                {
                    if (gridMap.isGridInRange(enemy.getGridId(), cre.getGridId(), cre.getAttackRange()))
                    {
                        enemyId = enemy.getGridId();
                        break;
                    }
                }
            }
            if (enemyId != -1)
            {
                BattleMsg msg = new BulletMsg(cre.getGridId(), enemyId, isServer, clock);
                msgList.add(msg);
            }
        }
    }


    public void updateBullet()
    {
        ArrayList<Bullet> tempList = new ArrayList<>();
        for (Bullet bullet : bullets)
        {
//            System.out.print(bullet.originX);
            bullet.update();
            if (bullet.isDead())
            {
                pane.getChildren().remove(bullet.getImgView());
                tempList.add(bullet);
            }
        }
        for (Bullet bullet : tempList)
        {
            bullets.remove(bullet);
        }
//        System.out.println();
    }

    public ArrayList<BattleMsg> bulletCollisionEvent(long clock)
    {
        ArrayList<BattleMsg> msgList = new ArrayList<>();
        for (Bullet bullet : bullets)
        {
            Creature[] creatures;
            if (bullet.isFromServer())
            {
                creatures = monsters;
            }
            else
            {
                creatures = calabashBrothers;
            }
            detectBulletCollision(msgList, bullet, creatures, clock);
        }
        for (BattleMsg msg : msgList)
        {
            parseMsg(msg);
        }
        return msgList;
    }


    private void detectBulletCollision(ArrayList<BattleMsg> msgList, Bullet bullet,
                                       Creature[] creatures, long clock)
    {
        for (Creature cre : creatures)
        {
            if (cre != null && !cre.isDied())
            {
                if (!bullet.isUsed() && cre.getGridId() == gridMap.posToGridId(bullet.getX(), bullet.getY()))
                {
                    Point2D p=gridMap.gridIdToCreaturePos(cre.getGridId());
                    bullet.setCollision(p.getX(), p.getY());
                    if (bullet.isFromServer() == isServer)
                    {
                        BattleMsg msg = new DamageMsg(cre.getName(), bullet.getDamage(), isServer, clock);
                        msgList.add(msg);
                    }
                }
            }
        }
    }


    public void addCalabashBrother(Creature gd)
    {
        creatureMap.put(gd.getName(), gd);
        int id = gd.getGridId();
        calabashBrothers[id] = gd;
        setPosById(id, gd);
    }

    public void addMonster(Creature monster)
    {
        creatureMap.put(monster.getName(), monster);
        int id = monster.getGridId();
        monsters[id] = monster;
        setPosById(id, monster);
    }

    public boolean isCalabashWin()
    {
        for (Creature monster : monsters)
        {
            if (monster != null && !monster.isDied())
            {
                return false;
            }
        }
        return true;
    }

    public boolean isMonsterWin()
    {
        for (Creature calabash : calabashBrothers)
        {
            if (calabash != null && !calabash.isDied())
            {
                return false;
            }
        }
        return true;
    }
}