package cn.edu.nju.map;

import cn.edu.nju.SceneSwitch;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController
{
    private SceneSwitch ss;

    public LoginController(SceneSwitch ss)
    {
        this.ss = ss;
        Image image = new Image("image/login/leftImage.png");
        leftImage = new ImageView(image);

        Image image1 = new Image("image/login/rightImage.png");
        rightImage = new ImageView(image1);

        Image image2 = new Image("image/login/lock.png");
        lockImage = new ImageView(image2);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(3));
        scaleTransition.setNode(Title);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }


    @FXML
    private Text Title;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button ExitButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button LoginButton;

    @FXML
    private Text LoginMessage;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userName;

    @FXML
    private Label PassWordLabel;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView leftImage;

    @FXML
    private ImageView rightImage;

    @FXML
    private ImageView lockImage;

    @FXML
    void SignUp(ActionEvent event) throws IOException
    {
        //Sing();
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
//        Node node = (Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        stage.setScene(new Scene(root));
        ss.changeToLoginScene();
    }


    public void ExitButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    public void RegisterAction(ActionEvent event)
    {
        //Parent root= FXMLLoader.load(getClass().getResourceAsStream("test.fxml"));
    }


    public void LoginMessage()
    {
        //Sing();
        if (userName.getText().isEmpty() == false && password.getText().isEmpty() == false)
        {
            LoginMessage.setText("You are trying to Login.");
            if (validLogin())
            {
                ss.changeToServerScene();
            }
        }
        else
        {
            LoginMessage.setText("Please enter your information.");
        }

    }

    public void LockFade()
    {
        System.out.println("hello here");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3.0));
        fadeTransition.setCycleCount(2);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setNode(lockImage);
        parallelTransition.getChildren().addAll(rotateTransition, fadeTransition);
        parallelTransition.play();

        //fadeTransition.play();
    }

    public boolean validLogin()
    {
        System.out.println("hello ha ");
        Database connection = new Database();
        Connection connection1 = connection.getDbconnection();
        System.out.println("hello haa ");
        String InputCheck = "select count(1) from user_password where name =\"" + userName.getText() +
                "\"and password=\"" + password.getText() + "\"";
        System.out.println(InputCheck);
        try
        {
            Statement statement = connection1.createStatement();
            ResultSet resultSet = statement.executeQuery(InputCheck);
            while (resultSet.next())
            {
                if (resultSet.getInt(1) == 1)
                {
                    LoginMessage.setText("Congratulations.");
                    return true;
                }
                else
                {
                    LoginMessage.setText("Sorry.");
                }
            }

        } catch (Exception E)
        {
            E.getCause();
            E.printStackTrace();
        }
        return false;
    }

//    void Sing()
//    {
//        Media media;
//        MediaPlayer mediaPlayer;
//
//        String s1 = Paths.get("../images/sound.mp3").toUri().toString();
//        media = new Media("../images/sound.mp3");
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
//        System.out.println("we are playing");
//    }
}
