import java.awt.*;
import java.net.Socket;
import java.util.Observable;

public class ChatThread extends Observable implements Runnable {


    public boolean isServer() {
        return true;
    }

    public void setName(String name) {

    }

    public void setColor(String colStr) {

    }

    public void sendMessage(String msg) {

    }

    public void disconnect() {

    }

    @Override
    public void run() {

    }

    public ChatThread(Socket socket) {

    }
}
