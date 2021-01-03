package cn.edu.nju.component;

import cn.edu.nju.constant.Constant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Bullet
{
    private int clock = 1;
    private int speed = 0;
    private ImageView imgView;
    private final boolean isGood;
    private final int damage;
    private final Direction direction;
    private final BulletType bulletType;
    private final double originX;
    private final double originY;
    private double animationX;
    private double animationY;
    private final double range;
    private Status status = Status.FLYING;

    private int totalFrame = 0;
    private int frameIndex = -1;
    private Image[] frameList;

    enum Status
    {
        FLYING, EXPLODING, DEAD
    }


    public Bullet(BulletType bulletType, Direction direction, boolean isGood,
                  int damage, double x, double y, double animationX, double animationY, double range)
    {
        this.isGood = isGood;
        this.damage = damage;
        this.direction = direction;
        this.bulletType = bulletType;
        this.originX = x;
        this.originY = y;
        this.animationX = animationX;
        this.animationY = animationY;
        this.range = range;
        initBulletAnimation();

        imgView.setX(x);
        imgView.setY(y);
    }

    private void initBulletAnimation()
    {
        if (bulletType == BulletType.NORMAL)
        {
            setBulletByDirection(Constant.NORMAL_BULLET_PREFIX);
            this.clock = 30;
            this.speed = 0;
        }
        else if (bulletType == BulletType.FIRE)
        {
            setBulletByDirection(Constant.FIRE_BULLET_PREFIX);
            this.clock = 10000;
            this.speed = 3;
            this.frameList = Constant.FIRE_FRAME_LIST;
            this.totalFrame = this.frameList.length;
        }
        else if (bulletType == BulletType.WATER)
        {
            setBulletByDirection(Constant.WATER_BULLET_PREFIX);
            this.clock = 10000;
            this.speed = 3;
            this.frameList = Constant.WATER_FRAME_LIST;
            this.totalFrame = this.frameList.length;

        }
        else if (bulletType == BulletType.DARK)
        {
            this.imgView = new ImageView(Constant.DARK_BULLET);
            this.clock = 10000;
            this.speed = 3;
            this.frameList = Constant.DARK_FRAME_LIST;
            this.totalFrame = this.frameList.length;
        }
        else if (bulletType == BulletType.FLASH)
        {
            setBulletByDirection(Constant.FLASH_BULLET_PREFIX);
            this.clock = 10000;
            this.speed = 5;
            this.frameList = Constant.FLASH_FRAME_LIST;
            this.totalFrame = this.frameList.length;
        }
        else if (bulletType == BulletType.SOIL)
        {
            this.clock = 10;
            this.speed = 3;
            this.frameList = Constant.SOIL_FRAME_LIST;
            this.totalFrame = this.frameList.length;
            this.imgView = new ImageView(this.frameList[0]);
        }
        else if (bulletType == BulletType.DARK_POWER)
        {
            this.clock = 10;
            this.speed = 3;
            this.frameList = Constant.DARK_POWER_LIST;
            this.totalFrame = this.frameList.length;
            this.imgView = new ImageView(this.frameList[0]);
        }
        else
        {
            assert false;
        }
    }


    private void setBulletByDirection(String prefix)
    {
        String imgUri = prefix;
        if (direction == Direction.RIGHT)
        {
            imgUri += "_right.png";
        }
        else if (direction == Direction.LEFT)
        {
            imgUri += "_left.png";
        }
        else if (direction == Direction.UP)
        {
            imgUri += "_up.png";
        }
        else
        {
            imgUri += "_down.png";
        }
        this.imgView = new ImageView(imgUri);
    }


    public ImageView getImgView()
    {
        return imgView;
    }


    public void update()
    {
        if (this.status == Status.FLYING)
        {
            updateFlyingBullet();
        }
        else if (this.status == Status.EXPLODING)
        {
            updateExplodingBullet();
        }
    }

    private void updateFlyingBullet()
    {
        this.clock -= 1;
        if (this.clock <= 0 && (bulletType == BulletType.NORMAL ||
                bulletType == BulletType.DARK_POWER || bulletType == BulletType.SOIL))
        {
            this.status = Status.DEAD;
        }
        if (this.direction == Direction.RIGHT)
        {
            imgView.setX(imgView.getX() + speed);
            if (imgView.getX() - originX >= range)
            {
                this.status = Status.DEAD;
            }
        }
        if (this.direction == Direction.LEFT)
        {
            imgView.setX(imgView.getX() - speed);
            if (originX - imgView.getX() >= range)
            {
                this.status = Status.DEAD;
            }
        }
        if (this.direction == Direction.UP)
        {
            imgView.setY(imgView.getY() + speed);
            if (imgView.getY() - originY >= range)
            {
                this.status = Status.DEAD;
            }
        }
        if (this.direction == Direction.DOWN)
        {
            imgView.setY(imgView.getY() - speed);
            if (originY - imgView.getY() >= range)
            {
                this.status = Status.DEAD;
            }
        }
    }

    private void updateExplodingBullet()
    {
        this.clock -= 1;
        if (this.clock <= 0)
        {
            if (this.frameIndex == this.totalFrame - 1)
            {
                this.status = Status.DEAD;
            }
            else
            {
                this.frameIndex += 1;
                this.clock = 10;
                this.imgView.setImage(this.frameList[this.frameIndex]);
            }
        }
    }

    public void setCollision(double animationX, double animationY)
    {
        if (bulletType != BulletType.NORMAL)
        {
            this.clock = 0;
            this.imgView.setX(animationX);
            this.imgView.setY(animationY);
        }
        this.animationX = animationX;
        this.animationY = animationY;
        this.status = Status.EXPLODING;
    }

    public boolean isUsed()
    {
        return this.status != Status.FLYING;
    }

    public boolean isDead()
    {
        return this.status == Status.DEAD;
    }

    public boolean isFromServer()
    {
        return isGood;
    }

    public double getX()
    {
        return imgView.getX();
    }

    public double getY()
    {
        return imgView.getY();
    }

    public int getDamage()
    {
        return this.damage;
    }
}
