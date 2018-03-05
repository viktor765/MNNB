package thread.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OutgoingRequest {
    private final Socket socket;
    private final boolean accepted;

    private static final String correctReq = "<request></request>";
    private static final String acceptReq = "<request reply=\"yes\"></request>";
    private static final String denyReq = "<request reply=\"no\"></request>";

    public Socket getSocket() {
        return socket;
    }

    public boolean isAccepted() {
        return accepted;
    }

    private void send() throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(correctReq);
    }

    private boolean receive() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String received = in.readLine();

        if(received.equals(acceptReq)) {
            return true;
        } else if(received.equals(denyReq)) {
            return false;
        } else {
            throw new IOException("Bad reply XML");
        }
    }

    public OutgoingRequest(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        send();
        accepted = receive();
    }
}
