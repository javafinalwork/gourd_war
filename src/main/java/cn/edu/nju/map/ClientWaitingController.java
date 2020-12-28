package cn.edu.nju.map;

import cn.edu.nju.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;

public class ClientWaitingController
{
    SceneSwitch ss;

    @FXML
    private TextField ipAddressText;

    @FXML
    private Button confirmBtn;


    public ClientWaitingController(SceneSwitch ss)
    {
        this.ss = ss;
    }


    @FXML
    void completeInput(ActionEvent event)
    {
        Effect effect = new DropShadow();
//        Effect effect1 = new Lighting();
        confirmBtn.setEffect(effect);
//        confirmBtn.setEffect(effect1);
        ss.connectToServer(ipAddressText.getText());
        System.out.println(ipAddressText.getText());
    }

}