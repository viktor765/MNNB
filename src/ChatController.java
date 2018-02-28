import java.awt.event.ActionEvent;

public class ChatController {
    public ChatController(ChatThread chatThread) {
        ChatPanel chatPanel = new ChatPanel(chatThread);
        
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