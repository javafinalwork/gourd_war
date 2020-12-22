package cn.edu.nju.map;

import cn.edu.nju.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUp
{
    SceneSwitch ss;

    public SignUp(SceneSwitch ss)
    {
        this.ss = ss;
        Image image = new Image("image/login/leftImage.png");
        leftImage = new ImageView(image);

        Image image1 = new Image("image/login/rightImage.png");
        rightImage = new ImageView(image1);

        Image image2 = new Image("image/login/lock.png");
        lockImage = new ImageView(image2);
    }

    @FXML
    private ImageView leftImage;

    @FXML
    private Text Title;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button ExitButton;

    @FXML
    private ImageView rightImage;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView lockImage;

    @FXML
    private Text LoginMessage;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Label PassWordLabel;

    @FXML
    private TextField userName;

    @FXML
    private Label userNameLabel;

    @FXML
    void ExitButtonAction(ActionEvent event)
    {
        System.out.println("exist");
        Stage stage = (Stage) ExitButton.getScene().getWindow();
//        Sing();
        stage.close();
    }

    @FXML
    void LockFade(MouseEvent event)
    {

    }

    @FXML
    void SignIn(ActionEvent event) throws IOException
    {
        ss.changeToLoginScene();
    }

    @FXML
    void LoginMessage(ActionEvent event)
    {
//        Sing();
        System.out.println("loginMessage");
        Database database = new Database();
        String user = userName.getText();
        String pass = password.getText();
        Connection connection = database.getDbconnection();
        String insertStatement = "insert into user_password(name,password) values('" + user + "','" + pass + "')";
        System.out.println(insertStatement);
        try
        {
            System.out.println("ragister db");
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate(insertStatement);
            if (status > 0)
            {
                System.out.println("successed.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void RegisterButtonClicked(MouseEvent event)
    {
        Database database = new Database();
        String user = userName.getText();
        String pass = password.getText();
        Connection connection = database.getDbconnection();
        try
        {
            System.out.println("register db");
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into user_password(id,name,password) values('" + user + "','" + pass + "')");
            if (status > 0)
            {
                System.out.println("success.");
            }
        } catch (SQLException e)
        {

        }
    }

//
//    void Sing()
//    {
//        Media media;
//        MediaPlayer mediaPlayer;
//
//        String s1 = Paths.get("../images/sound.mp3").toUri().toString();
//        media = new Media(s1);
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
//        System.out.println("we are playing");
//    }
}
