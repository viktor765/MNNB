package chat;


import thread.HostChatThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

public class HostChatPanel extends ChatPanel {
    private final JButton kickButton;
    private final HostChatThread chatThread;

    public void addKickListener(ActionListener actionListener) {
        kickButton.addActionListener((ActionEvent e) -> {
            List<String> addresses = chatThread.getAddresses().stream()
                    .map(inetAddress -> inetAddress.toString())
                    .map(address -> address.substring(1))
                    .collect(Collectors.toList());

            Object[] a = addresses.toArray();

            String defaultAddress = "";

            if(!addresses.isEmpty()) {
                defaultAddress = addresses.get(0);
            }

            InetAddress kickedIp = (InetAddress) JOptionPane.showInputDialog(
                    this.getMyFrame(), "Choose client to kick:", "Kick",
                    JOptionPane.QUESTION_MESSAGE, null, chatThread.getAddresses().toArray(), defaultAddress);
            if(kickedIp != null) {
                actionListener.actionPerformed(new ActionEvent(
                        kickButton, (int) AWTEvent.TEXT_EVENT_MASK, kickedIp.toString().substring(1)
                ));
            }
        });
    }

    private HostChatPanel(HostChatThread hostChatThread) {
        super(hostChatThread);
        this.chatThread = hostChatThread;
        getMyFrame().setTitle("Chat, local server");
        kickButton = new JButton("Kick");
        this.add(kickButton);

        getMyFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static HostChatPanel instance = null;

    public static HostChatPanel getInstance(HostChatThread hostChatThread) {
        if(instance == null) {
            instance = new HostChatPanel(hostChatThread);
        }

        return instance;
    }
}
