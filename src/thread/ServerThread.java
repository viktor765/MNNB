package thread;

import message.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

class ServerThread implements Runnable, Observer {
    private ServerSocket serverSocket;
    private final List<ConnectionThread> clientThreads = new LinkedList<>();

    public void kickClient(InetAddress address) {
        ConnectionThread client = clientThreads.stream()
                .filter(connectionThread -> connectionThread.getAddress() == address)
                .findFirst().get();

        client.disconnect();
        clientThreads.remove(client);
    }

    public List<InetAddress> getAddresses() {
        return clientThreads.stream()
                .map(connectionThread -> connectionThread.getAddress())
                //.filter(InetAddress::isAnyLocalAddress)//Är kommenterat av testskäl
                .collect(Collectors.toList());
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
        if(arg instanceof Message && o instanceof ConnectionThread) {
            for(ConnectionThread c : clientThreads) {
                c.sendString(((Message)arg).toXML());
            }
        }
    }

    private ServerThread(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private static ServerThread instance = null;

    public static ServerThread getInstance(int port) throws IOException {
        if (instance == null) {
            instance = new ServerThread(port);
            return instance;
        } else {
            throw new IOException("ServerThread instance already exists.");
        }
    }
}
