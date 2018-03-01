package test;

import thread.ChatThread;

import java.net.Socket;
import java.util.Observer;

public class RemoteClientTest {
    private static ChatThread chatThread;

    private static final String ip = "130.237.226.17";
    private static final int port = 4443;

    private static Observer chatObs = (o, arg) -> System.out.println("chatView: " + arg);

    public static void main(String[] args) throws Exception {
        test();

        Thread.sleep(1000);

        //System.exit(1);
    }

    private static void test() throws Exception {
        Socket clientSocket = new Socket(ip, port);
        chatThread = new ChatThread(clientSocket);
        chatThread.addObserver(chatObs);

        new Thread(chatThread).start();

        chatThread.setName("Viktor");
        chatThread.setColor("00ff00");

        chatThread.sendMessage("Ett meddelande från Viktor.");
        chatThread.sendMessage("Ett till meddelande från Viktor.");

        //chatThread.disconnect();
        Thread.sleep(1000);

        chatThread.sendMessage("Meddelande efter nedkoppling.");
    }
}
