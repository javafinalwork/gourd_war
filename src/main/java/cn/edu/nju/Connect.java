package cn.edu.nju;

import javafx.application.Platform;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Runnable;

class BaseConnector implements Runnable
{
    SceneSwitch ss;
    Battlefield battlefield;

    BaseConnector(SceneSwitch ss, Battlefield battlefield)
    {
        this.ss = ss;
        this.battlefield = battlefield;
    }


    @Override
    public void run()
    {

    }

    public void write(BattleMsg msg) throws IOException
    {

    }

    public void close()
    {
    }

}


class DataServer extends BaseConnector
{
    ObjectOutputStream serverOos;
    ObjectInputStream clientOis;
    ;

    DataServer(SceneSwitch ss, Battlefield battlefield)
    {
        super(ss, battlefield);
    }

    @Override
    public void run()
    {
        try
        {
            startServer();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void startServer() throws IOException, ClassNotFoundException
    {
        ServerSocket server = new ServerSocket(59091);
        Socket clientSocket = server.accept();

        clientOis = new ObjectInputStream(clientSocket.getInputStream());
        serverOos = new ObjectOutputStream(clientSocket.getOutputStream());
        // 回调FX函数在非FX线程
        Platform.runLater(() -> ss.finishConnect());

        BattleMsg msg = null;
        while ((msg = (BattleMsg) clientOis.readObject()) != null)
        {
            if (msg.msgType == MsgType.FINISH_MSG)
            {
                break;
            }
            battlefield.parseMsg(msg);
        }
        clientSocket.shutdownInput();
        clientSocket.shutdownOutput();
        clientSocket.close();
    }

    @Override
    public void write(BattleMsg msg) throws IOException
    {
        serverOos.writeObject(msg);
//        serverOos.flush();
    }

}


class DataClient extends BaseConnector
{
    String host;
    int port;
    ObjectOutputStream clientOos;
    ObjectInputStream serverOis;

    DataClient(String host, int port, SceneSwitch ss, Battlefield battlefield)
    {
        super(ss, battlefield);
        this.host = host;
        this.port = port;
    }


    @Override
    public void run()
    {
        try
        {
            startClient();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void startClient() throws IOException, ClassNotFoundException
    {
        Socket echoSocket = new Socket(host, 59091);

        clientOos = new ObjectOutputStream(echoSocket.getOutputStream());
        serverOis = new ObjectInputStream(echoSocket.getInputStream());
        Platform.runLater(() -> ss.finishConnect());
        BattleMsg msg;
        try
        {
            while ((msg = (BattleMsg) serverOis.readObject()) != null)
            {
                if (msg.msgType == MsgType.FINISH_MSG)
                {
                    break;
                }
                battlefield.parseMsg(msg);
            }
        } catch (EOFException ignored)
        {}
        echoSocket.shutdownInput();
        //echo msg
        write(new BattleMsg(MsgType.FINISH_MSG, false, 0));
        echoSocket.shutdownOutput();
        echoSocket.close();

    }

    @Override
    public void write(BattleMsg msg) throws IOException
    {
//        clientOos.reset();
        clientOos.writeObject(msg);
//        clientOos.flush();
    }


}
