package cn.edu.nju.map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import cn.edu.nju.SceneSwitch;


public class StartController
{
    Stage stage;
    SceneSwitch ss;
    @FXML
    private ImageView SoundControlButton;

    @FXML
    private Button startButton;

    @FXML
    private Button ExitButton;

    @FXML
    private Button replayButton;

    @FXML
    private Button selectMapButton;

    @FXML
    void exitAction(ActionEvent event)
    {
    }

    public StartController(SceneSwitch ss)
    {
        this.ss = ss;
    }

    @FXML
    void soundOff(MouseEvent event)
    {

    }

    @FXML
    void startGame(ActionEvent event)
    {
        ss.changeToMapChooseScene();
    }

    @FXML
    void playRecord(ActionEvent event)
    {

    }

    @FXML
    void selectMap(ActionEvent event)
    {

    }

}
