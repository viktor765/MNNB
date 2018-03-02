package thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class HostChatThread extends ChatThread {
    private static final String localHost = "127.0.0.1";

    private final ServerThread serverThread;

    public void kick(InetAddress address) {
        serverThread.kickClient(address);
    }

    public List<InetAddress> getAddresses() {
        return serverThread.getAddresses();
    }

    private HostChatThread(Socket socket, ServerThread serverThread) throws IOException {
        super(socket);
        this.serverThread = serverThread;

        setName("host");
        setColor("0000ff");
    }

    private static HostChatThread instance = null;

    public static HostChatThread getInstance(ServerThread serverThread, int port) throws IOException {
        if(instance == null) {
            Socket socket = new Socket(localHost, port);
            instance = new HostChatThread(socket, serverThread);

            return instance;
        } else {
            throw new IOException("HostChatThread instance already exists.");
        }
    }
}
