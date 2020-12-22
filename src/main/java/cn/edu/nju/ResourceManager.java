package cn.edu.nju;

public class ResourceManager
{
    boolean[] calabashBrothers = new boolean[35];
    boolean[] monsters = new boolean[35];

    ResourceManager()
    {
        for (int i = 0; i < 35; i += 7)
        {
            calabashBrothers[i] = true;
        }
        monsters[6] = true;
        monsters[13] = true;
    }

    public boolean lockResource(BattleMsg msg)
    {
        if (msg.msgType == MsgType.MOVE_MSG)
        {
            int srcId = msg.getSrcId();
            int dstId = msg.getDstId();
            if (msg.isFromServer())
            {
                if (monsters[dstId] == false)
                {
                    calabashBrothers[srcId] = false;
                    calabashBrothers[dstId] = true;
                    return true;
                }
            }
            else
            {
                if (calabashBrothers[dstId] == false)
                {
                    monsters[srcId] = false;
                    monsters[dstId] = true;
                    return true;
                }
            }
        }
        return false;
    }

}
