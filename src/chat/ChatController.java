package chat;

import thread.ChatThread;
import thread.HostChatThread;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatController {
    private final ChatThread chatThread;
    private final ChatPanel chatPanel;

    protected ChatPanel getChatPanel() {
        return chatPanel;
    }

    private void addListeners(ChatPanel chatPanel, ChatThread chatThread) {
        chatPanel.addCloseListener((ActionEvent e) -> {
            chatThread.disconnect();

            chatPanel.close();
        });

        chatPanel.addNameListener((ActionEvent e) -> chatThread.setName(e.getActionCommand()));
        chatPanel.addColorListener((ActionEvent e) -> chatThread.setColor(e.getActionCommand()));

        chatPanel.addSendListener((ActionEvent e) -> chatThread.sendMessage(chatPanel.getMessageFieldText()));
    }

    protected ChatController(ChatThread chatThread, ChatPanel chatPanel) {
        this.chatThread = chatThread;
        this.chatPanel = chatPanel;

        chatThread.addObserver(chatPanel);

        addListeners(chatPanel, chatThread);
    }

    public ChatController(ChatThread chatThread) {
        this(chatThread, new ChatPanel(chatThread));
    }

    public ChatController(HostChatThread hostChatThread) {
        this(hostChatThread, HostChatPanel.getInstance(hostChatThread));

        //chatThread.kick tar nu en InetAddress från chatThread.getAddresses()
        ((HostChatPanel)getChatPanel()).addKickListener((ActionEvent e) -> {
            try {
                hostChatThread.kick(InetAddress.getByName(e.getActionCommand()));
            } catch (UnknownHostException ex) {
                System.out.println("Not an IP address");
            }
        });
    }
}