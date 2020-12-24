package cn.edu.nju.map;

import cn.edu.nju.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpController
{
    SceneSwitch ss;

    public SignUpController(SceneSwitch ss)
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
    private Text titleText;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView rightImage;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView lockImage;

    @FXML
    private Text loginMessage;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Label passWordLabel;

    @FXML
    private TextField userName;

    @FXML
    private Label userNameLabel;

    @FXML
    void exitButtonAction(ActionEvent event)
    {
        System.out.println("exist");
        Stage stage = (Stage) exitButton.getScene().getWindow();
//        Sing();
        stage.close();
    }

    @FXML
    void lockFade(MouseEvent event)
    {

    }

    @FXML
    void signInAction(ActionEvent event) throws IOException
    {
        ss.changeToLoginScene();
    }

    @FXML
    void loginAction(ActionEvent event)
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
    void registerButtonClickedAction(MouseEvent event)
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
