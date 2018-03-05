package chat;

import thread.ChatThread;

import java.awt.event.ActionEvent;

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
}