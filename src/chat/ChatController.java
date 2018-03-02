package chat;

import thread.ChatThread;
import thread.HostChatThread;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatController {
    public ChatController(ChatThread chatThread) {
        ChatPanel chatPanel = new ChatPanel(chatThread);
        this.addListeners(chatPanel, chatThread);
    }

    public ChatController(HostChatThread hostChatThread) {
        HostChatPanel hostChatPanel = HostChatPanel.getInstance(hostChatThread);
        this.addListeners(hostChatPanel, hostChatThread);

        //chatThread.kick tar nu en InetAddress från chatThread.getAddresses()
        hostChatPanel.addKickListener((ActionEvent e) -> {
            try {
                hostChatThread.kick(InetAddress.getByName(e.getActionCommand()));
            } catch (UnknownHostException ex) {
                System.out.println("Not an IP address");
            }
        });
    }

    private void addListeners(ChatPanel chatPanel, ChatThread chatThread) {
        chatPanel.addCloseListener((ActionEvent e) -> {
            chatThread.disconnect();

            chatPanel.close();
        });

        chatPanel.addNameListener((ActionEvent e) -> chatThread.setName(e.getActionCommand()));
        chatPanel.addColorListener((ActionEvent e) -> chatThread.setColor(e.getActionCommand()));

        chatPanel.addSendListener((ActionEvent e) -> chatThread.sendMessage(chatPanel.getMessageFieldText()));

        chatThread.addObserver(chatPanel);
    }
}