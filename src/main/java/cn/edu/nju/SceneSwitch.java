package cn.edu.nju;

import cn.edu.nju.map.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import javafx.scene.text.Text;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;


public class SceneSwitch
{
    Stage stage;
    Scene startScene;
    Scene mapChooseScene;
    Scene loginScene;
    Scene signupScene;
    Scene connectScene;
    Scene serverScene;
    Scene clientScene;
    Scene finishScene;
    Battle battle;
    MapChange mapChange;
    Finish finish;
    final int WINDOW_WIDTH = 1280;
    final int WINDOW_HEIGHT = 700;

    public SceneSwitch(Stage stage)
    {
        this.stage = stage;
    }

    private void initStartScene()
    {
        Parent root;
        try
        {
            StartController sc = new StartController(this);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/start.fxml"));
            loader.setController(sc);
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        startScene = new Scene(root, 1200, 700);
    }

    private void initConnectScene()
    {
        Pane pane = new Pane();
        Image img1 = new Image("/image/temp1.jpg");
        pane.getChildren().add(new ImageView(img1));
        Button clientBtn = new Button("Client");
        clientBtn.relocate(300, 100);
        Button serverBtn = new Button("Server");
        serverBtn.relocate(300, 200);
        Button backBtn = new Button("返回");
        clientBtn.setOnAction(e -> stage.setScene(clientScene));
        serverBtn.setOnAction(e -> {
            stage.setScene(serverScene);
            battle = new Battle(this);
//            battle.startConnection();
            finishConnect();
        });
        backBtn.setOnAction(e -> stage.setScene(startScene));
        pane.getChildren().addAll(clientBtn, serverBtn, backBtn);
        connectScene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void finishConnect()
    {
        stage.setScene(battle.getScene());
        battle.start();
    }

    private InetAddress getLANAddressOnWindows()
    {
        try
        {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements())
            {
                NetworkInterface nif = nifs.nextElement();

                if (nif.getName().startsWith("wlan"))
                {
                    Enumeration<InetAddress> addresses = nif.getInetAddresses();

                    while (addresses.hasMoreElements())
                    {

                        InetAddress addr = addresses.nextElement();
                        if (addr.getAddress().length == 4)
                        {
                            return addr;
                        }
                    }
                }
            }
        } catch (SocketException ex)
        {
            ex.printStackTrace(System.err);
        }
        return null;
    }

    private void initServerScene()
    {
        Pane pane = new Pane();
        InetAddress addr;
        addr = getLANAddressOnWindows();
        if (addr == null)
        {
            return;
        }

        Text text = new Text(200, 100, "主机" + addr.getHostAddress() + "等待连接...");
        pane.getChildren().add(text);
        serverScene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void initClientScene()
    {
        FlowPane pane = new FlowPane();
        TextField textHost = new TextField("127.0.0.1");
        TextField textPort = new TextField("1");
        pane.getChildren().addAll(new Label("host"), textHost);
        pane.getChildren().addAll(new Label("port"), textPort);
        Button bt = new Button("connect");
        bt.setOnAction(e -> {
            battle = new Battle(textHost.getText(),
                    Integer.parseInt(textPort.getText()), this);
            battle.startConnection();
        });
        pane.getChildren().add(bt);
        clientScene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void initMapChooseScene()
    {
        mapChange = new MapChange(this);
        mapChooseScene = mapChange.getScene();
    }

    private void initFinishScene()
    {
        this.finish = new Finish(this);
        finishScene = this.finish.getScene();
    }

    private void initLoginScene()
    {
        Parent root;
        try
        {
            LoginController lc = new LoginController(this);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/login.fxml"));
            loader.setController(lc);
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        loginScene = new Scene(root, 1280, 700);
    }

    private void initSignupScene()
    {
        Parent root;
        try
        {
            SignUp signup = new SignUp(this);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/signup.fxml"));
            loader.setController(signup);
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        signupScene = new Scene(root, 1280, 700);
    }

    public void changeToMapChooseScene()
    {
        stage.setScene(mapChooseScene);
        stage.centerOnScreen();
    }

    public void changeToServerScene()
    {
        stage.setScene(serverScene);
    }

    public void changeToStartScene()
    {
        stage.setScene(startScene);
    }

    public void changeToLoginScene()
    {
        stage.setScene(loginScene);
    }

    public void changeToSignupScene()
    {
        stage.setScene(signupScene);
    }


    public void init()
    {
        initStartScene();
        initLoginScene();
        initSignupScene();
        initMapChooseScene();
        initConnectScene();
        initServerScene();
        initClientScene();
        initFinishScene();
    }

    public void start()
    {
        init();
        stage.setTitle("葫芦娃大战妖精");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setScene(startScene);
        stage.getIcons().add(Constant.PROJECT_ICON);
        stage.show();
    }
}
