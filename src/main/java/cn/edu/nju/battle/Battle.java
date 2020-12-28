package cn.edu.nju.battle;

import cn.edu.nju.constant.Constant;
import cn.edu.nju.SceneSwitch;
import cn.edu.nju.component.BulletType;
import cn.edu.nju.component.Creature;
import cn.edu.nju.component.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Battle
{
    SceneSwitch sceneSwitch;
    Scene battleScene;
    BaseConnector connector;
    boolean isServer = false;
    Pane pane = new Pane();
    Battlefield battlefield;
    long clock = 0;
    Recorder recorder;
    Timeline timeLine;

    public Battle(SceneSwitch ss)
    {
        this.sceneSwitch = ss;
        initBattle();
    }


    public Battle(SceneSwitch ss, boolean isServer, String host)
    {
        this.isServer = isServer;
        this.sceneSwitch = ss;
        recorder = new Recorder();
        initBattle();
        if (isServer)
        {
            connector = new DataServer(ss, battlefield);
        }
        else
        {
            connector = new DataClient(host, ss, battlefield);
        }
    }

    /**
     * 初始化战斗场景
     */
    private void initBattle()
    {
        pane.getChildren().add(new ImageView(Constant.BATTLE_IMG));
        battleScene = new Scene(pane, 1200, 700);
        battlefield = new Battlefield(isServer, pane, this, recorder);
    }

    /**
     * 初始化所有葫芦娃妖精站位
     */
    private void initCreature()
    {
        File file = new File(Constant.CREATURE_DATA_URI);
        String content = "";

        try
        {
            content = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        BulletType[] bulletTypes = {BulletType.NORMAL, BulletType.WATER, BulletType.FIRE,
                BulletType.DARK, BulletType.FLASH, BulletType.SOIL};
        JSONObject jsonObject = new JSONObject(content);
        for (Object obj : jsonObject.getJSONArray("calabash"))
        {
            JSONObject jo = (JSONObject) obj;
            Creature gd = new Creature(jo.getString("name"), jo.getInt("gridId"),
                    Direction.RIGHT, jo.getInt("hitPoint"), jo.getInt("attack"),
                    jo.getInt("speed"), jo.getInt("range"), bulletTypes[jo.getInt("bullet")],
                    jo.getString("imgUri"), true);
            battlefield.addCalabashBrother(gd);
            pane.getChildren().addAll(gd.getImgView(), gd.getRBorder(), gd.getRLife());
        }
        for (Object obj : jsonObject.getJSONArray("monster"))
        {
            JSONObject jo = (JSONObject) obj;
            Creature monster = new Creature(jo.getString("name"), jo.getInt("gridId"),
                    Direction.LEFT, jo.getInt("hitPoint"), jo.getInt("attack"),
                    jo.getInt("speed"), jo.getInt("range"), bulletTypes[jo.getInt("bullet")],
                    jo.getString("imgUri"), false);
            battlefield.addMonster(monster);
            pane.getChildren().addAll(monster.getImgView(), monster.getRBorder(), monster.getRLife());
        }
    }

    private void initCard()
    {
        double originX = 50;
        double originY = 20;
        for (int i = 0; i < 7; i++)
        {
            Constant.CARDS[i].relocate(originX + i * 120, originY);
            pane.getChildren().add(Constant.CARDS[i]);
        }
        Constant.CARDS[0].setOnMouseClicked(e -> {

        });
    }


    public void start()
    {
//        initCard();
        initCreature();
        catchClickOnPane();
        timeLine = new Timeline(new KeyFrame(Duration.seconds(0.01),
                e -> {
                    clock += 1;
                    catchCollision();
                    updateCreature();
                    updateBullet();
                    detectGameStatus();
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private void updateCreature()
    {
        battlefield.updateCreature();
    }

    private void updateBullet()
    {
        battlefield.updateBullet();
    }

    private void detectGameStatus()
    {
        boolean isCalabashWin = battlefield.isCalabashWin();
        boolean isMonsterWin = battlefield.isMonsterWin();
        if (isCalabashWin || isMonsterWin)
        {
            if (isServer)
            {
                writeMsg(new FinishMsg(isCalabashWin, isMonsterWin, true, clock));
                timeLine.stop();
            }
        }
    }


    /**
     * 捕获鼠标事件
     */
    private void catchClickOnPane()
    {
        pane.setOnMouseClicked(e -> {
            double x = e.getSceneX();
            double y = e.getSceneY();
//            System.out.println("x: " + x + " y: " + y);
            BattleMsg msg = battlefield.moveCreatureEvent(x, y, clock);
            writeMsg(msg);
        });
    }

    private void catchCollision()
    {
        ArrayList<BattleMsg> msgList = battlefield.attackEvent(clock);
        for (BattleMsg msg : msgList)
        {
            writeMsg(msg);
        }

        msgList = battlefield.bulletCollisionEvent(clock);
        for (BattleMsg msg : msgList)
        {
            writeMsg(msg);
        }
    }


    /**
     * 启动连接
     */
    public void startConnection()
    {
        new Thread(connector).start();
    }

    /**
     * 返回战斗场景
     */
    public final Scene getScene()
    {
        return battleScene;
    }

    /**
     * 向对方客户端或服务器传输消息
     */
    private void writeMsg(BattleMsg msg)
    {
        if (msg == null)
        {
            return;
        }
        try
        {
            connector.write(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendBack(BattleMsg msg)
    {
        writeMsg(msg);
    }

    public void playBack(File file)
    {
        LinkedList<BattleMsg> msgList = new LinkedList<>();
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object obj;
            while ((obj = ois.readObject()) != null)
            {
                BattleMsg msg = (BattleMsg) obj;
                msgList.add(msg);
            }
            ois.close();
        } catch (EOFException ignored)
        {
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        initCreature();
        timeLine = new Timeline(new KeyFrame(Duration.seconds(0.01),
                e -> {
                    clock += 1;
                    while (msgList.size() > 0 && clock >= msgList.getFirst().getClock())
                    {
                        BattleMsg msg = msgList.removeFirst();
                        battlefield.parseMsgOnPlayBack(msg);
                    }
                    battlefield.updateBulletCollisionOnPlayBack();
                    updateCreature();
                    updateBullet();
                    if (msgList.size() == 0)
                    {
                        timeLine.stop();
                        sceneSwitch.changeToStartScene();
                    }
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
}
