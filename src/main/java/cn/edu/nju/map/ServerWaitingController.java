package cn.edu.nju.map;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



public class ServerWaitingController
{
    private final Scene scene;

    public ServerWaitingController(String uri)
    {
        Text ipText = new Text("Server" + uri + "等待连接...");
        ImageView imgView = new ImageView("image/login/rightImage.png");
        Pane pane=new Pane();
        pane.getChildren().add(imgView);
        pane.getChildren().add(ipText);
        ipText.setLayoutX(302.0);
        ipText.setLayoutY(292.0);
        ipText.setStrokeType(StrokeType.OUTSIDE);
        ipText.setWrappingWidth(745.9);
        ipText.setFont(new Font(46.0));
        imgView.setFitWidth(1280);
        imgView.setFitHeight(700);
        imgView.setPickOnBounds(true);
        scene=new Scene(pane);
    }

    public Scene getScene()
    {
        return this.scene;
    }

}
