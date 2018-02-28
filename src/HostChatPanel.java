import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostChatPanel extends ChatPanel {
    private final JButton kickButton;
    private String[] ips = {"hola"};

    public HostChatPanel(ChatThread chatThread) {
        super(chatThread);
        kickButton = new JButton("Kick");
        this.add(kickButton);
    }

    public void addKickListener(ActionListener actionListener) {
        kickButton.addActionListener((ActionEvent e) -> {
            String kickedIp = (String) JOptionPane.showInputDialog(this.getMyFrame(), "Choose client to kick:", "Kick",
                    JOptionPane.QUESTION_MESSAGE, null, ips, ips[0]);

            if(kickedIp != null){
                actionListener.actionPerformed(new ActionEvent(kickButton, (int) AWTEvent.TEXT_EVENT_MASK, kickedIp));
            }
        });
    }
}
