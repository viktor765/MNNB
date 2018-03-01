package test;

import chat.ChatController;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observer;
import javax.swing.JFrame;
import thread.ChatThread;
import thread.HostChatThread;
import thread.ServerThread;


public class TestKick {
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

        //System.exit(1);
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
        
        ChatController chatController = new ChatController((HostChatThread)chatThread);
        
        
        InetAddress address = serverThread.getAddresses().get(0);
    }
}
