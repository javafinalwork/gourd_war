package cn.edu.nju.constant;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Constant
{
    //misc
    public final static int portNumber = 59091;
    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 700;
    public final static String GAME_TITLE = "葫芦娃大战妖精";

    //image
    public final static String CREATURE_DATA_URI = "src/main/resources/data/creature.json";
    public final static Image PROJECT_ICON = new Image("/image/gourd_icon.png");
    public final static Image GRAY_GRID0 = new Image("/image/graygrid0.png");
    public final static Image GRAY_GRID1 = new Image("/image/graygrid1.png");
    public final static Image GRAY_GRID2 = new Image("/image/graygrid2.png");
    public final static String NORMAL_BULLET_PREFIX = "/image/attack";
    public final static String FIRE_BULLET_PREFIX = "/image/fire";
    public final static String WATER_BULLET_PREFIX = "/image/water";
    public final static String FLASH_BULLET_PREFIX = "/image/flash";
//    public final static String RECOVER_BULLET_PREFIX = "/image/recover";
    public final static Image DARK_BULLET = new Image("/image/dark.png");
    public final static Image CALABASH_DEAD = new Image("/image/tomb1.png");
    public final static Image MONSTER_DEAD = new Image("/image/tomb2.png");
    public final static ImageView[] CARDS = getCards();
    public final static Image[] BATTLE_IMAGES = {
            new Image("/image/map_choose/battle.png"),
            new Image("/image/map_choose/battle1.png"),
            new Image("/image/map_choose/battle2.png")
    };

    //animation
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
    public final static Image[] RECOVERY_FRAME_LIST = getExplodeImageList(5,
            "/image/recovery/recover");
    public final static Image[] DARK_POWER_LIST = getExplodeImageList(6,
            "/image/dark_power/dark");

    //fxml
    public static final String START_FXML = "/fxml/start.fxml";
    public static final String CLIENT_WAITING_FXML = "/fxml/clientWaiting.fxml";
    public static final String LOGIN_FXML = "/fxml/login.fxml";
    public static final String SIGNUP_FXML = "/fxml/signup.fxml";

    private static ImageView[] getCards()
    {
        ImageView[] cards = new ImageView[7];
        cards[0] = new ImageView("/image/card/card_red.png");
        cards[1] = new ImageView("/image/card/card_orange.png");
        cards[2] = new ImageView("/image/card/card_yellow.png");
        cards[3] = new ImageView("/image/card/card_green.png");
        cards[4] = new ImageView("/image/card/card_cyan.png");
        cards[5] = new ImageView("/image/card/card_blue.png");
        cards[6] = new ImageView("/image/card/card_purple.png");
        return cards;
    }


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
