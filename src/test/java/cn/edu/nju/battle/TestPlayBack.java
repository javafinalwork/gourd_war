package cn.edu.nju.battle;

import org.junit.Test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static org.junit.Assert.*;

public class TestPlayBack
{
    @Test
    public void testRecord()
    {
        String filename="myTest";
        Recorder recorder = new Recorder(filename);
        recorder.writeToFile(new MoveMsg(1, 0, true, 30));
        BattleMsg msg = null;
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            Object obj;
            while ((obj = ois.readObject()) != null)
            {
                msg = (BattleMsg) obj;
            }
            ois.close();
        } catch (EOFException ignored)
        {
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return;
        }
        assertEquals(msg.getSrcId(), 1);
        assertEquals(msg.getDstId(), 0);
        assertTrue(msg.isServer);
        assertEquals(msg.getClock(), 30);
    }

}
