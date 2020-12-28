package cn.edu.nju.battle;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recorder
{
    ObjectOutputStream oos;

    Recorder()
    {
        String name = generateFileName();

        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(name));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    Recorder(String filename)
    {
        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void writeToFile(BattleMsg msg)
    {
        try
        {
            oos.writeObject(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 生成通过当前时间生成的存档文件名
     */
    private String generateFileName()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();// 获取当前时间
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }
}
