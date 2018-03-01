import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerThread implements Runnable, Observer {
    private ServerSocket serverSocket;
    private final List<ConnectionThread> clientThreads = new LinkedList<>();

    public void kickClient(String ip) {
        System.out.println("Server kick " + ip);
    }

    public List<String> getIPs() {
        List<String> IPs = new LinkedList<>();

        for(ConnectionThread th : clientThreads) {
            IPs.add(th.getIP());
        }

        return IPs;
    }

    private void acceptClient() {
        try {
            Socket clientSocket = serverSocket.accept();
            ConnectionThread connectionThread = new ConnectionThread(clientSocket);
            clientThreads.add(connectionThread);//temporärt, måste göras med <request>
            connectionThread.addObserver(this);

            new Thread(connectionThread).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            acceptClient();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Message) {
            for(ConnectionThread c : clientThreads) {
                c.sendString(((Message)arg).toXML());
            }
        }
    }

    public ServerThread(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
}
