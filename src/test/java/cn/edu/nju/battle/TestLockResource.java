package cn.edu.nju.battle;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLockResource
{
    @Test
    public void testLockResources()
    {
        ResourceManager rm=new ResourceManager();
        boolean invalidMove=rm.lockResource(new MoveMsg(0,6, true, 0));
        assertFalse(invalidMove);
        boolean validMove=rm.lockResource(new MoveMsg(0,1, true, 0));
        assertTrue(validMove);
        invalidMove =rm.lockResource(new MoveMsg(6,1,false,0));
        assertFalse(invalidMove);
    }
}
