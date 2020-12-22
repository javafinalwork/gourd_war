package cn.edu.nju;

import javafx.scene.image.Image;

public class Constant
{
    public final static Image BATTLE_IMG = new Image("/image/battle.png");
    public final static String CREATURE_DATA_URI = "src/main/resources/data/creature.json";
    public final static Image PROJECT_ICON = new Image("/image/gourd_icon.png");
    public final static Image GRAY_GRID = new Image("/image/graygrid.png");
    public final static String NORMAL_BULLET_PREFIX = "/image/attack";
    public final static String FIRE_BULLET_PREFIX = "/image/fire";
    public final static String WATER_BULLET_PREFIX = "/image/water";
    public final static String FLASH_BULLET_PREFIX = "/image/flash";
    public final static Image DARK_BULLET = new Image("/image/dark.png");
    public final static Image CALABASH_DEAD = new Image("/image/tomb1.png");
    public final static Image MONSTER_DEAD = new Image("/image/tomb2.png");
    public final static Image[] WATER_FRAME_LIST = getExplodeImageList(9,
            "/image/water_explode/water");
    public final static Image[] FIRE_FRAME_LIST = getExplodeImageList(8,
            "/image/fire_explode/fire");
    public final static Image[] DARK_FRAME_LIST = getExplodeImageList(10,
            "/image/dark_explode/dark");
    public final static Image[] FLASH_FRAME_LIST = getExplodeImageList(6,
            "/image/flash_explode/flash");
    public final static Image[] SOIL_FRAME_LIST = getExplodeImageList(8,
            "/image/soil_explode/soil");


    private static Image[] getExplodeImageList(final int num, String uriPrefix)
    {
        Image[] imgList = new Image[num];
        for (int i = 1; i <= num; i++)
        {
            String imgUri = uriPrefix + i + ".png";
            imgList[i - 1] = new Image(imgUri);
        }
        return imgList;
    }

}