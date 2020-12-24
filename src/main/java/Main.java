import cn.edu.nju.SceneSwitch;
import cn.edu.nju.map.ActivationController;
import com.sun.javafx.application.LauncherImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import cn.edu.nju.map.Loader;

public class Main extends Application
{
//    @Override
//    public void init() throws Exception
//    {
//        super.init();
//        for (int i = 1; i <= 10; i++)
//        {
//            double progress = i / 10.0;
//            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
//            Thread.sleep(700);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception
    {

//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/activation.fxml"));
//        stage.setScene(new Scene(root, 1280, 700));
//        stage.show();
//        ActivationController.label.setText("加载完毕！");
//        ActivationController.textField.setText("");
//        ActivationController.progress.setProgress(1);
//        String words = "欢迎来到葫芦娃大战妖精游戏，祝您玩的愉快。";
//        final IntegerProperty i = new SimpleIntegerProperty(0);
//        Timeline timeline = new Timeline();
//
//        KeyFrame keyFrame = new KeyFrame(
//                Duration.seconds(0.3), event -> {
//            if (i.get() > words.length())
//            {
//                timeline.stop();
//            }
//            else
//            {
//                ActivationController.textField.setText(words.substring(0, i.get()));
////                Sing();
//                i.set(i.get() + 1);
//            }
//        }
//        );
//        timeline.getKeyFrames().addAll(keyFrame);
//        timeline.setCycleCount(22);
//        timeline.play();
        new SceneSwitch(stage).start();
    }

    public static void main(String[] args)
    {
//        LauncherImpl.launchApplication(Main.class, Loader.class, args);
        Main.launch();
    }

//    void Sing()
//    {
//        MediaPlayer mediaPlayer = new MediaPlayer(new Media("media/typeSong.mp3"));
//        mediaPlayer.play();
//    }
}