package cn.edu.nju.map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cn.edu.nju.SceneSwitch;

import java.io.File;


public class StartController
{
    SceneSwitch ss;
    @FXML
    private ImageView soundControlButton;

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button replayButton;

    @FXML
    private Button selectMapButton;

    @FXML
    void exitAction(ActionEvent event)
    {
        ss.exitGame();
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
        ss.changeToLoginScene();
    }

    @FXML
    void playRecord(ActionEvent event)
    {
        ss.openRecordFile();
    }

    @FXML
    void selectMap(ActionEvent event)
    {

    }
}
