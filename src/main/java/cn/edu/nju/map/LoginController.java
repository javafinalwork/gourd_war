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
        scaleTransition.setNode(titleText);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }


    @FXML
    private Text titleText;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button loginButton;

    @FXML
    private Text loginMessage;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userName;

    @FXML
    private Label passWordLabel;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView leftImage;

    @FXML
    private ImageView rightImage;

    @FXML
    private ImageView lockImage;


    @FXML
    private Button serverButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button touristButton;

    boolean isServerClicked = false;
    boolean isClientClicked = false;
    boolean isJourneyClicked = false;


    @FXML
    void signUpAction(ActionEvent event) throws IOException
    {
//        Sing();
        ss.changeToSignupScene();
    }


    public void exitButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void registerAction(ActionEvent event)
    {
        //Parent root= FXMLLoader.load(getClass().getResourceAsStream("test.fxml"));
    }


    public void loginAction()
    {
        //Sing();
        if(!isServerClicked&&!isClientClicked)
        {
            loginMessage.setText("Please choose server or client!");
            return;
        }
        if(isJourneyClicked)
        {
            ss.changeToConnectScene(isServerClicked);
            return;
        }
        if (!userName.getText().isEmpty() && !password.getText().isEmpty())
        {
            loginMessage.setText("You are trying to Login.");
            if (isValidLogin())
            {
                ss.changeToConnectScene(isServerClicked);
            }
        }
        else
        {
            loginMessage.setText("Please enter your information.");
        }
    }

    public void lockFade()
    {
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
    }

    public boolean isValidLogin()
    {
        Database connection = new Database();
        Connection connection1 = connection.getDbconnection();
        String InputCheck = "select count(1) from user_password where name =\"" + userName.getText() +
                "\"and password=\"" + password.getText() + "\"";
        try
        {
            Statement statement = connection1.createStatement();
            ResultSet resultSet = statement.executeQuery(InputCheck);
            while (resultSet.next())
            {
                if (resultSet.getInt(1) == 1)
                {
                    loginMessage.setText("Congratulations.");
                    return true;
                }
                else
                {
                    loginMessage.setText("Sorry.");
                }
            }

        } catch (Exception E)
        {
            E.getCause();
            E.printStackTrace();
        }
        return false;
    }

    @FXML
    void serverAction(ActionEvent event)
    {
        if (isServerClicked)
        {
            serverButton.setStyle("-fx-background-color: #ADD8E6");
        }
        else
        {
            isClientClicked = false;
            serverButton.setStyle("-fx-background-color:#FF0000");
            clientButton.setStyle("-fx-background-color: #ADD8E6");
        }
        isServerClicked = !isServerClicked;
        //修改颜色
    }

    @FXML
    void touristAction(ActionEvent event)
    {
        if (isJourneyClicked)
        {
            touristButton.setStyle("-fx-background-color: #ADD8E6");
        }
        else
        {
            touristButton.setStyle("-fx-background-color:#FF0000");
        }
        isJourneyClicked = !isJourneyClicked;
    }

    @FXML
    void clientAction(ActionEvent event)
    {
        if (isClientClicked)
        {
            clientButton.setStyle("-fx-background-color: #ADD8E6");
        }
        else
        {
            isServerClicked = false;
            clientButton.setStyle("-fx-background-color:#FF0000");
            serverButton.setStyle("-fx-background-color: #ADD8E6");
        }
        isClientClicked = !isClientClicked;
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
