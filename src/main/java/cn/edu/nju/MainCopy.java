package cn.edu.nju;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainCopy extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        new SceneSwitch(stage).start();
    }

    public static void main(String[] args)
    {
        MainCopy.launch();
    }


}