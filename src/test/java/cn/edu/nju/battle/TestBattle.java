//package cn.edu.nju.battle;
//
//import cn.edu.nju.SceneSwitch;
//import cn.edu.nju.component.BulletType;
//import cn.edu.nju.component.Creature;
//import cn.edu.nju.component.Direction;
//import cn.edu.nju.constant.Constant;
//import javafx.stage.Stage;
//import org.apache.commons.io.FileUtils;
//import org.json.JSONObject;
//import static org.junit.Assert.*;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class TestBattle
//{
//    @Test
//    public void testJsonParse()
//    {
//
//        File file = new File(Constant.CREATURE_DATA_URI);
//        String content = "";
//        try
//        {
//            content = FileUtils.readFileToString(file, "UTF-8");
//        } catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        BulletType[] bulletTypes = {BulletType.NORMAL, BulletType.WATER, BulletType.FIRE,
//                BulletType.DARK, BulletType.FLASH, BulletType.SOIL};
//        JSONObject jsonObject = new JSONObject(content);
//        ArrayList<Creature> gourdList=new ArrayList<>();
//        for(Object obj :jsonObject.getJSONArray("calabash"))
//        {
//            JSONObject jo = (JSONObject) obj;
//            Creature gd = new Creature(jo.getString("name"), jo.getInt("gridId"),
//                    Direction.RIGHT, jo.getInt("hitPoint"), jo.getInt("attack"),
//                    jo.getInt("speed"), jo.getInt("range"), bulletTypes[jo.getInt("bullet")],
//                    jo.getString("imgUri"), true);
//            gourdList.add(gd);
//        }
//        assertEquals(gourdList.get(0).getName(), "大娃");
//        assertEquals(gourdList.size(), 7);
//
//        ArrayList<Creature> monsterList=new ArrayList<>();
//        for(Object obj :jsonObject.getJSONArray("monster"))
//        {
//            JSONObject jo = (JSONObject) obj;
//            Creature monster = new Creature(jo.getString("name"), jo.getInt("gridId"),
//                    Direction.LEFT, jo.getInt("hitPoint"), jo.getInt("attack"),
//                    jo.getInt("speed"), jo.getInt("range"), bulletTypes[jo.getInt("bullet")],
//                    jo.getString("imgUri"), false);
//            monsterList.add(monster);
//        }
//        assertEquals(monsterList.get(0).getName(), "蛇精");
//        assertEquals(monsterList.size(), 2);
//    }
//
//    @Test
//    public void testConnect()
//    {
//        SceneSwitch ss=new SceneSwitch(new Stage());
//        Battle serverBattle=new Battle(ss);
//        Battle clientBattle=new Battle("127.0.0.1", ss);
//        serverBattle.startConnection();
//        clientBattle.startConnection();
//
//    }
//
//}
