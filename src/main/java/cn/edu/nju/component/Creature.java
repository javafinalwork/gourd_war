package cn.edu.nju.component;

import cn.edu.nju.constant.Constant;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Creature
{
    enum Status
    {
        FIGHTING, MOVING, DEAD
    }

    private final boolean isGood;
    private static final int LIFE_REC_WIDTH = 80;
    private static final int LIFE_REC_HEIGHT = 5;
    private Direction direction;// 判断方向，用于选择形象
    private final String name;
    private int gridId;
    private final int totalHp;
    private int hitPoint;// 生命值
    private final int attack;// 攻击力
    private final int attackRange;
    private final int speed;
    private final BulletType bulletType;
    private ImageView imgView;
    private Rectangle rLife;
    private Rectangle rBorder;
    int triggeredTime = 100;
    int clock = 0;
    Status status = Status.FIGHTING;
    double dstX;
    double dstY;


    public Creature(String name, int gridId, Direction direction,
             int hitPoint, int attack, int speed, int attackRange,
             BulletType bulletType, String imgUri, boolean isGood)
    {
        this.name = name;
        this.gridId = gridId;
        this.direction = direction;
        this.totalHp = hitPoint;
        this.hitPoint = hitPoint;
        this.attack = attack;
        this.speed = speed;
        this.attackRange = attackRange;
        this.bulletType = bulletType;
        this.isGood = isGood;
        this.imgView = new ImageView(imgUri);

        rBorder = new Rectangle(0, 0, LIFE_REC_WIDTH, LIFE_REC_HEIGHT);
        rBorder.xProperty().bind(imgView.xProperty().add(20));
        rBorder.yProperty().bind(imgView.yProperty().add(120));
        rBorder.setStroke(Color.BLACK);
        rBorder.setFill(Color.TRANSPARENT);
        rLife = new Rectangle(0, 0, LIFE_REC_WIDTH, LIFE_REC_HEIGHT);
        rLife.setFill(Color.RED);
        rLife.xProperty().bind(imgView.xProperty().add(20));
        rLife.yProperty().bind(imgView.yProperty().add(120));
    }

    // 移动
    public void move(int x, int y)
    {
        imgView.setX(x);
        imgView.setY(y);
    }

    public String getName()
    {
        return this.name;
    }


    public boolean isDied()
    {
        return hitPoint <= 0;
    }

    public ImageView getImgView()
    {
        return imgView;
    }

    public Rectangle getRLife()
    {
        return rLife;
    }

    public Rectangle getRBorder()
    {
        return rBorder;
    }


    public int getGridId()
    {
        return gridId;
    }

    public void setGridId(int gridId)
    {
        this.gridId = gridId;
    }

    public void setDamage(int damage)
    {
        if (this.hitPoint <= 0)
        {
            return;
        }
        this.hitPoint -= damage;
        if (this.hitPoint <= 0)
        {
            this.hitPoint = 0;
            changeDeadImg();
        }
        double ratio = (double) this.hitPoint / this.totalHp;
        this.rLife.setWidth(ratio * LIFE_REC_WIDTH);
    }

    private void changeDeadImg()
    {
        if (isGood)
        {
            this.imgView.setImage(Constant.CALABASH_DEAD);
        }
        else
        {
            this.imgView.setImage(Constant.MONSTER_DEAD);
        }
        this.imgView.setX(imgView.getX()+30);
        this.imgView.setY(imgView.getY()+30);
        rLife.setVisible(false);
        rBorder.setVisible(false);
    }

    public int getAttack()
    {
        return this.attack;
    }

    public int getAttackRange()
    {
        return attackRange;
    }

    public BulletType getBulletType()
    {
        return bulletType;
    }

    public void update()
    {
        if (status == Status.MOVING)
        {
            clock = 0;
            if (this.direction == Direction.RIGHT)
            {
                double x = imgView.getX() + speed;
                if (x > dstX)
                {
                    x = dstX;
                    this.status = Status.FIGHTING;
                }
                imgView.setX(x);
            }
            if (this.direction == Direction.LEFT)
            {
                double x = imgView.getX() - speed;
                if (x < dstX)
                {
                    x = dstX;
                    this.status = Status.FIGHTING;
                }
                imgView.setX(x);
            }
            if (this.direction == Direction.UP)
            {
                double y = imgView.getY() + speed;
                if (y > dstY)
                {
                    y = dstY;
                    this.status = Status.FIGHTING;
                }
                imgView.setY(y);
            }
            if (this.direction == Direction.DOWN)
            {
                double y = imgView.getY() - speed;
                if (y < dstY)
                {
                    y = dstY;
                    this.status = Status.FIGHTING;
                }
                imgView.setY(y);
            }
        }
        else
        {
            clock += 1;
        }
    }

    public void startMove(double dstX, double dstY, Direction direction)
    {
        this.status = Status.MOVING;
        this.dstX = dstX;
        this.dstY = dstY;
        this.direction = direction;
    }

    public boolean isTriggered()
    {
        if (clock >= triggeredTime)
        {
            clock = 0;
            return true;
        }
        return false;
    }
}