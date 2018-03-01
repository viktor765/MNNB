package test;

import chat.ChatController;
import main.MainModel;
import thread.ChatThread;
import thread.HostChatThread;

import java.util.Observer;


public class TestKick {
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
        
        ChatController chatController = new ChatController((HostChatThread)chatThread);
    }
}
