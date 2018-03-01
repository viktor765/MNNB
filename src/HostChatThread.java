import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class HostChatThread extends ChatThread {
    private final ServerThread serverThread;

    public void kick(String ip) {
        setChanged();
        notifyObservers(ip);
    }

    public List<String> getIPs() {
        return serverThread.getIPs();
    }

    public HostChatThread(Socket socket, ServerThread serverThread) throws IOException {
        super(socket);
        this.serverThread = serverThread;
    }
}
