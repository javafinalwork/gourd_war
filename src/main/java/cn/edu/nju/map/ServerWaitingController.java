package cn.edu.nju.map;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ServerWaitingController
{
    @FXML
    private Text IP;

    public static Text ipaddress;


    public void ChangeContent()
    {
        //TODO
    }

    ServerWaitingController(String ipaddr)
    {
        ipaddress = IP;
        ipaddress.setText("ip地址");
    }
}
