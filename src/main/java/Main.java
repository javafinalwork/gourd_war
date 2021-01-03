import cn.edu.nju.SceneSwitch;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        new SceneSwitch(stage).start();
    }

    public static void main(String[] args)
    {
        Main.launch();
    }
}