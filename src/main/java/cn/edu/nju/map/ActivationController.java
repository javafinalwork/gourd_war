package cn.edu.nju.map;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ActivationController implements Initializable
{

    @FXML
    private Line progressBar;

    @FXML
    private Label progressLabel;

    public static Label label;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private ImageView Image;

    @FXML
    private Text Text;

    public static ImageView imageView;

    public static Text textField;

    public static ProgressBar progress;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        label = progressLabel;
        progress = progressbar;
        imageView = Image;
        textField = Text;
    }
}
