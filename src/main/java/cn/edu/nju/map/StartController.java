package cn.edu.nju.map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import cn.edu.nju.SceneSwitch;


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


    public void enter_l(KeyEvent event)
    {

        if (event.getCode() == KeyCode.L)
        {
            ss.openRecordFile();
        }
    }

}
