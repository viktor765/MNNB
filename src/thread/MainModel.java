package thread;

import java.io.IOException;
import java.net.Socket;

public class MainModel {
    private final ServerThread serverThread;
    private final HostChatThread hostChatThread;

    public HostChatThread getHostChatThread() {
        return hostChatThread;
    }

    public ChatThread connectToChat(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);//temporärt, måste göras med <request>
        return new ChatThread(socket);
    }

    private MainModel(int serverPort) throws IOException {
        serverThread = ServerThread.getInstance(serverPort);
        new Thread(serverThread).start();
        hostChatThread = HostChatThread.getInstance(serverThread, serverPort);
        new Thread(hostChatThread).start();
    }

    private static MainModel instance = null;

    public static MainModel getInstance(int serverPort) throws IOException {
        if(instance == null) {
            instance = new MainModel(serverPort);
        }

        return instance;
    }
}
