package thread;

import thread.request.IncomingRequest;
import thread.request.OutgoingRequest;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class MainModel extends Observable implements Observer {
    private final ServerThread serverThread;
    private  HostChatThread hostChatThread;

    public HostChatThread getHostChatThread() {
        return hostChatThread;
    }

    public ChatThread connectToChat(String ip, int port) throws IOException {
        OutgoingRequest req = new OutgoingRequest(ip, port);

        if(req.isAccepted()) {
            Socket socket = new Socket(ip, port);
            return new ChatThread(socket);
        } else {
            return null;//ugly
        }
    }

    public void accept(IncomingRequest request) {
        serverThread.accept(request);
    }

    public void update(Observable o, Object message) {
        if(o instanceof ServerThread && message instanceof IncomingRequest) {
            setChanged();
            notifyObservers(message);
        }
    }

    private MainModel(int serverPort) throws IOException {
        serverThread = ServerThread.getInstance(serverPort);
        new Thread(serverThread).start();
        hostChatThread = HostChatThread.getInstance(serverThread, serverPort);
        new Thread(hostChatThread).start();

        serverThread.addObserver(this);
    }

    private static MainModel instance = null;

    public static MainModel getInstance(int serverPort) throws IOException {
        if(instance == null) {
            instance = new MainModel(serverPort);
        }

        return instance;
    }
}
