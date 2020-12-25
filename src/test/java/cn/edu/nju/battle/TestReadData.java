package cn.edu.nju.battle;

import cn.edu.nju.constant.Constant;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestReadData
{
    @Test
    public void testJsonParse()
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
        JSONObject jsonObject = new JSONObject(content);
        ArrayList<String> gourdNameList = new ArrayList<>();
        gourdNameList.add("大娃");
        gourdNameList.add("二娃");
        gourdNameList.add("三娃");
        gourdNameList.add("四娃");
        gourdNameList.add("五娃");
        gourdNameList.add("六娃");
        gourdNameList.add("七娃");
        int i = 0;
        for (Object obj : jsonObject.getJSONArray("calabash"))
        {
            JSONObject jo = (JSONObject) obj;
            assertEquals(jo.getString("name"), gourdNameList.get(i));
            i += 1;
        }

        ArrayList<String> monsterNameList = new ArrayList<>();
        monsterNameList.add("蝎子精");
        monsterNameList.add("蛇精");
        i = 0;
        for (Object obj : jsonObject.getJSONArray("monster"))
        {
            JSONObject jo = (JSONObject) obj;
            assertEquals(jo.getString("name"), monsterNameList.get(i));
            i += 1;
        }
    }



}
