package test;

import message.Message;
import thread.ChatThread;
import thread.ConnectionThread;

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Tester {
    private static ConnectionThread connectionThread;
    private static ChatThread chatThread;

    private static Observer conObs = (o, arg) -> System.out.println("connection: " + arg);
    private static Observer chatObs = (o, arg) -> System.out.println("chat: " + arg);

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4444);
        Socket connection = new Socket("127.0.0.1", 4444);
        Socket clientSocket = serverSocket.accept();

        connectionThread = new ConnectionThread(connection);
        chatThread = new ChatThread(clientSocket);

        connectionThread.addObserver(conObs);
        chatThread.addObserver(chatObs);

        new Thread(connectionThread).start();
        new Thread(chatThread).start();

        chatThread.setName("Kalle");
        chatThread.setColor("0000ff");

        test();
    }

    private static void test() {
        chatThread.sendMessage("Ett meddelande från Kalle");

        //connectionThread.sendString("<message sender=\"Stina\" color=\"0000ff\">Ett vidarebefordrat meddelande</message>");
        connectionThread.sendString(new Message("Ett vidarebefordrat meddelande från stina", "Stina", Color.MAGENTA).toXML());

        chatThread.disconnect();
    }
}
