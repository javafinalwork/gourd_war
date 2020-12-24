package cn.edu.nju.map;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Loader extends Preloader
{
    private Stage preloaderStage;
    private Scene scene;
    public static int num = 0;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        preloaderStage = primaryStage;
        primaryStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    @Override
    public void init() throws Exception
    {
        super.init();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/activation.fxml"));
        scene = new Scene(root, 1280, 700);
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info)
    {
        super.handleApplicationNotification(info);
        if (info instanceof ProgressNotification)
        {
            ActivationController.label.setText("Loading" + (((ProgressNotification) info).getProgress()) * 100 + "%");
            ActivationController.progress.setProgress(((ProgressNotification) info).getProgress());
            num++;
            if (num == 6)
            {
                num = 1;
            }
            String name = String.valueOf(num);
            name = "image/activation/gourd" + name + ".png";
            Image image = new Image(name, true);
            ActivationController.imageView.setImage(image);
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info)
    {
        super.handleStateChangeNotification(info);
        StateChangeNotification.Type type = info.getType();
        switch (type)
        {
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }
}
