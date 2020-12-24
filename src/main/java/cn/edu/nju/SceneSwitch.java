package cn.edu.nju;

import cn.edu.nju.battle.Battle;
import cn.edu.nju.constant.Constant;
import cn.edu.nju.map.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.text.Text;

import java.io.File;
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
    Scene serverWaitingScene;
    Scene serverScene;
    Scene clientScene;
    Scene finishScene;
    Battle battle;
    MapChange mapChange;
    FinishController finishController;
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
            battle = new Battle(textHost.getText(), this);
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
        this.finishController = new FinishController(this);
        finishScene = this.finishController.getScene();
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
            SignUpController signup = new SignUpController(this);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/signup.fxml"));
            loader.setController(signup);
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        signupScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void initServerWaitingScene()
    {

        InetAddress addr;
        String s;
        addr = getLANAddressOnWindows();
        if (addr == null)
        {
            try
            {
                addr = InetAddress.getLocalHost();
            } catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
            s = addr.getHostAddress();
        }
        else
        {
            s = addr.getHostAddress();
        }
        ServerWaitingController swc = new ServerWaitingController(s);
        serverWaitingScene = swc.getScene();
    }


    public void changeToMapChooseScene()
    {
        stage.setScene(mapChooseScene);
        stage.centerOnScreen();
    }

    public void changeToConnectScene(boolean isServerClicked)
    {
        if (isServerClicked)
        {
            stage.setScene(mapChooseScene);

        }
        else
        {
            stage.setScene(clientScene);
        }

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

    public void changeToFinishScene(boolean isGourdWin, boolean isMonsterWin)
    {
        stage.setScene(finishScene);
    }

    public void changeToServerWaitingScene()
    {
        stage.setScene(serverWaitingScene);
        battle = new Battle(this);
        battle.startConnection();
//            finishConnect();
    }


    public void exitGame()
    {
        stage.close();
    }

    public void openRecordFile()
    {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null)
        {
            System.out.println("choose file");
            battle = new Battle();
            stage.setScene(battle.getScene());
            battle.playBack(file);
        }
    }


    public void init()
    {
        initStartScene();
        initLoginScene();
        initSignupScene();
        initMapChooseScene();
        initServerWaitingScene();
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
        stage.setScene(finishScene);
        stage.getIcons().add(Constant.PROJECT_ICON);
        stage.show();
    }
}
