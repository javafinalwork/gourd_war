package cn.edu.nju;

import cn.edu.nju.battle.Battle;
import cn.edu.nju.constant.Constant;
import cn.edu.nju.map.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;


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

    private void initClientScene()
    {
        Parent root;
        try
        {
            ClientWaitingController cwc = new ClientWaitingController(this);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/clientWaiting.fxml"));
            loader.setController(cwc);
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        clientScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
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

    public void connectToServer(String ipAddr)
    {
        battle = new Battle(this, false, ipAddr);
        battle.startConnection();
    }


    public void changeToConnectScene(boolean isServerClicked)
    {
        if (isServerClicked)
        {
            initMapChooseScene();
            stage.setScene(mapChooseScene);
        }
        else
        {
            initClientScene();
            stage.setScene(clientScene);
        }
    }

    public void changeToStartScene()
    {
        initStartScene();
        stage.setScene(startScene);
    }

    public void changeToLoginScene()
    {
        initLoginScene();
        stage.setScene(loginScene);
    }

    public void changeToSignupScene()
    {
        initSignupScene();
        stage.setScene(signupScene);
    }

    public void changeToFinishScene(boolean isGourdWin, boolean isMonsterWin)
    {
        initFinishScene();
        stage.setScene(finishScene);
    }

    public void changeToServerWaitingScene()
    {
        initServerWaitingScene();
        stage.setScene(serverWaitingScene);
        battle = new Battle(this, true, "");
        battle.startConnection();
//        finishConnect();
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
            battle = new Battle(this);
            stage.setScene(battle.getScene());
            battle.playBack(file);
        }
    }


    public void start()
    {
        stage.setTitle("葫芦娃大战妖精");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        changeToStartScene();
        stage.getIcons().add(Constant.PROJECT_ICON);
        stage.show();
    }
}
