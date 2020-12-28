package cn.edu.nju.battle;

import java.io.Serializable;

/**
 * 消息类型
 */
enum MsgType
{
    MOVE_MSG, DAMAGE_MSG, BULLET_MSG, FINISH_MSG
}

/**
 * 战斗消息基类，供序列化
 */
class BattleMsg implements Serializable
{
    public static final long serialVersionUID = 12345;
    public final MsgType msgType;
    protected boolean isServer;
    long clock;

    BattleMsg(MsgType msgType, boolean isServer, long clock)
    {
        this.msgType = msgType;
        this.isServer = isServer;
        this.clock = clock;

    }

    public int getSrcId()
    {
        return -1;
    }

    public boolean isFromServer()
    {
        return isServer;
    }

    public int getDstId()
    {
        return -1;
    }

    public int getDamage()
    {
        return 0;
    }

    public String getName()
    {
        return "";
    }

    public long getClock()
    {
        return this.clock;
    }

    public boolean isCalabashWin()
    {
        return false;
    }

    public boolean isMonsterWin()
    {
        return false;
    }


    @Override
    public String toString()
    {
        return "BattleMsg{" +
                "msgType=" + msgType +
                ", isServer=" + isServer +
                ", clock=" + clock +
                '}';
    }
}

/**
 * 移动消息
 */
class MoveMsg extends BattleMsg
{
    int srcId;
    int dstId;

    MoveMsg(int srcId, int dstId, boolean isServer, long clock)
    {
        super(MsgType.MOVE_MSG, isServer, clock);
        this.srcId = srcId;
        this.dstId = dstId;
    }

    @Override
    public int getSrcId()
    {
        return this.srcId;
    }

    @Override
    public int getDstId()
    {
        return this.dstId;
    }


    @Override
    public String toString()
    {
        return "MoveMsg{" +
                "srcId" + srcId +
                "dstId=" + dstId +
                '}';
    }
}

/**
 * 攻击消息
 */
class DamageMsg extends BattleMsg
{
    int damage;
    String name;

    DamageMsg(String name, int damage, boolean isServer, long clock)
    {
        super(MsgType.DAMAGE_MSG, isServer, clock);
        this.damage = damage;
        this.name = name;
    }

    @Override
    public int getDamage()
    {
        return damage;
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}

class BulletMsg extends BattleMsg
{
    int srcId;
    int dstId;

    BulletMsg(int srcId, int dstId, boolean isServer, long clock)
    {
        super(MsgType.BULLET_MSG, isServer, clock);
        this.srcId = srcId;
        this.dstId = dstId;
    }

    @Override
    public int getSrcId()
    {
        return this.srcId;
    }

    @Override
    public int getDstId()
    {
        return dstId;
    }
}

class FinishMsg extends BattleMsg
{
    boolean isCalabashWin;
    boolean isMonsterWin;

    FinishMsg(boolean isCalabashWin, boolean isMonsterWin, boolean isServer, long clock)
    {
        super(MsgType.FINISH_MSG, isServer, clock);
        this.isCalabashWin = isCalabashWin;
        this.isMonsterWin = isMonsterWin;
    }

    @Override
    public boolean isCalabashWin()
    {
        return isCalabashWin;
    }

    @Override
    public boolean isMonsterWin()
    {
        return isMonsterWin;
    }
}
