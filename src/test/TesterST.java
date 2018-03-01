package test;

import main.MainModel;
import thread.ChatThread;
import thread.HostChatThread;

import java.net.InetAddress;
import java.util.Observer;

public class TesterST {
    private static ChatThread chatThread;

    private static final int port = 4444;

    private static Observer chatObs = (o, arg) -> System.out.println("chatView: " + arg);

    public static void main(String[] args) throws Exception {
        chatThread = MainModel.getInstance(port).getHostChatThread();
        chatThread.addObserver(chatObs);
        new Thread(chatThread).start();

        test();

        Thread.sleep(1000);
        System.exit(1);
    }

    private static void test() throws Exception {
        chatThread.setName("Kalle");
        chatThread.setColor("0000ff");

        chatThread.sendMessage("Ett meddelande från Kalle.");
        chatThread.sendMessage("Ett till meddelande från Kalle.");

        Thread.sleep(2000);
        InetAddress address = ((HostChatThread)chatThread).getAddresses().get(0);
        System.out.println(address);
        //((HostChatThread)chatThread).kick(address);

        chatThread.sendMessage("Meddelande efter kick.");
        Thread.sleep(1000);

        chatThread.disconnect();
        Thread.sleep(1000);

        chatThread.sendMessage("Meddelande efter nedkoppling.");
    }
}
