package thread;

import thread.request.OutgoingRequest;

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
            OutgoingRequest outgoingRequest = new OutgoingRequest(localHost, port);

            if(outgoingRequest.isAccepted()) {
                instance = new HostChatThread(outgoingRequest.getSocket(), serverThread);
                return instance;
            } else {
                throw new IOException("outgoingRequest was not accepted.");
            }
        } else {
            throw new IOException("HostChatThread instance already exists.");
        }
    }
}
