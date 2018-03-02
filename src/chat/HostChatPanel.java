package chat;


import thread.HostChatThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class HostChatPanel extends ChatPanel {
    private final JButton kickButton;
    private final HostChatThread chatThread;

    public void addKickListener(ActionListener actionListener) {
        kickButton.addActionListener((ActionEvent e) -> {
            InetAddress kickedIp = (InetAddress) JOptionPane.showInputDialog(this.getMyFrame(), "Choose client to kick:", "Kick",
                    JOptionPane.QUESTION_MESSAGE, null, chatThread.getAddresses().toArray(), chatThread.getAddresses().get(0));
            if(kickedIp != null) {
                actionListener.actionPerformed(new ActionEvent(kickButton, (int) AWTEvent.TEXT_EVENT_MASK, kickedIp.toString()));
            }
        });
    }

    private HostChatPanel(HostChatThread HostChatThread) {
        super(HostChatThread);
        this.chatThread = HostChatThread;
        getMyFrame().setTitle("Chat, local server");
        kickButton = new JButton("Kick");
        this.add(kickButton);
    }

    private static HostChatPanel instance = null;

    public static HostChatPanel getInstance(HostChatThread hostChatThread) {
        if(instance == null) {
            instance = new HostChatPanel(hostChatThread);
        }

        return instance;
    }
}
