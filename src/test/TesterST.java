package test;

import thread.ChatThread;
import thread.HostChatThread;
import thread.ServerThread;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Observer;

public class TesterST {
    private static ServerThread serverThread;
    private static ChatThread chatThread;

    private static final int port = 4444;

    private static Observer chatObs = (o, arg) -> System.out.println("chatView: " + arg);

    public static void main(String[] args) throws Exception {
        serverThread = ServerThread.getInstance(port);
        Thread thread = new Thread(serverThread);
        thread.start();

        test();

        Thread.sleep(1000);

        System.exit(1);
    }

    private static void test() throws Exception {
        Socket clientSocket = new Socket("127.0.0.1", port);
        chatThread = HostChatThread.getInstance(clientSocket, serverThread);
        chatThread.addObserver(chatObs);

        new Thread(chatThread).start();

        chatThread.setName("Kalle");
        chatThread.setColor("0000ff");

        chatThread.sendMessage("Ett meddelande från Kalle.");
        chatThread.sendMessage("Ett till meddelande från Kalle.");

        Thread.sleep(2000);
        InetAddress address = serverThread.getAddresses().get(0);
        System.out.println(address);
        ((HostChatThread)chatThread).kick(address);

        Thread.sleep(3000);

        chatThread.sendMessage("Meddelande efter kick.");

        chatThread.disconnect();
        Thread.sleep(1000);

        chatThread.sendMessage("Meddelande efter nedkoppling.");
    }
}
