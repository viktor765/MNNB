package chat;

import thread.ChatThread;
import thread.HostChatThread;

import java.awt.event.ActionEvent;

public class ChatController {
    public ChatController(ChatThread chatThread) {
        ChatPanel chatPanel = new ChatPanel(chatThread);
        this.addListeners(chatPanel, chatThread);
    }

    public ChatController(HostChatThread hostChatThread){
        HostChatPanel hostChatPanel = new HostChatPanel(hostChatThread);
        this.addListeners(hostChatPanel, hostChatThread);

        //chatThread.kick tar nu en InetAddress från chatThread.getAddresses()
        //hostChatPanel.addKickListener((ActionEvent e) -> hostChatThread.kick(e.getActionCommand()));
    }

    private void addListeners(ChatPanel chatPanel, ChatThread chatThread){
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