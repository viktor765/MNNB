package thread;

import message.Message;
import thread.request.IncomingRequest;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

class ServerThread extends Observable implements Runnable, Observer {
    private ServerSocket serverSocket;
    private final List<ConnectionThread> clientThreads = new LinkedList<>();

    public void kickClient(InetAddress address) {
        System.out.println("Kicked " + address);

        for (ConnectionThread client : clientThreads) {
            if(client.getAddress().toString().equals(address.toString())) {
                client.sendString(new Message("You have been kicked.", "host", Color.GRAY).toXML());
                client.disconnect();
                clientThreads.remove(client);
            }
        }
    }

    public List<InetAddress> getAddresses() {
        return clientThreads.stream()
                .map(ConnectionThread::getAddress)
                .filter(inetAddress -> !inetAddress.toString().equals("/127.0.0.1"))
                .collect(Collectors.toList());
    }

    public void accept(IncomingRequest request) {
        try {
            request.accept();

            ConnectionThread connectionThread = new ConnectionThread(request.getSocket());
            clientThreads.add(connectionThread);
            connectionThread.addObserver(this);

            new Thread(connectionThread).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClient() {
        try {
            Socket socket = serverSocket.accept();
            IncomingRequest incomingRequest = new IncomingRequest(socket);

            if(socket.getInetAddress().toString().equals("/127.0.0.1")) {//acceptera localhost automatiskt
                this.accept(incomingRequest);
            } else {
                setChanged();
                notifyObservers(incomingRequest);
            }
        } catch (IOException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
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
