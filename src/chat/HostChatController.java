package chat;

import thread.HostChatThread;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostChatController extends ChatController {
    private HostChatController(HostChatThread hostChatThread) {
        super(hostChatThread, HostChatPanel.getInstance(hostChatThread));

        //chatThread.kick tar nu en InetAddress från chatThread.getAddresses()
        ((HostChatPanel)getChatPanel()).addKickListener((ActionEvent e) -> {
            try {
                hostChatThread.kick(InetAddress.getByName(e.getActionCommand()));
            } catch (UnknownHostException ex) {
                System.out.println("Not an IP address");
            }
        });
    }

    private static HostChatController instance = null;

    public static HostChatController getInstance(HostChatThread hostChatThread) {
        if(instance == null) {
            instance = new HostChatController(hostChatThread);
        }

        return instance;
    }
}
