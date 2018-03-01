package test;

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;

public class TesterST {
    private static ServerThread serverThread;
    private static ChatThread chatThread;
    private static ChatThread chatThread2;

    private static final int port = 4443;

    private static Observer chatObs = (o, arg) -> System.out.println("chat: " + arg);

    public static void main(String[] args) throws Exception {
        serverThread = new ServerThread(port);
        Thread thread = new Thread(serverThread);
        thread.start();

        test();

        Thread.sleep(1000);
        System.exit(1);
    }

    private static void test() throws Exception {
        Socket clientSocket = new Socket("127.0.0.1", port);
        chatThread = new ChatThread(clientSocket);
        chatThread.addObserver(chatObs);

        new Thread(chatThread).start();

        chatThread.setName("Kalle");
        chatThread.setColor("0000ff");

        chatThread.setName("Kalle");
        chatThread.setColor("0000ff");

        chatThread.sendMessage("Ett meddelande från Kalle.");
        chatThread.sendMessage("Ett till meddelande från Kalle.");

        System.out.println(serverThread.getIPs().get(0));

        Thread.sleep(1000);
        chatThread.disconnect();
    }
}
