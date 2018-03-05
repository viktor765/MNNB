package thread.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IncomingRequest {
    private final Socket socket;
    private boolean hasReplied = false;

    private static final String correctReq = "<request></request>";
    private static final String acceptReq = "<request reply=\"yes\"></request>";
    private static final String denyReq = "<request reply=\"no\"></request>";

    public Socket getSocket() {
        return socket;
    }

    public String getIP() {
        return socket.getInetAddress().toString();
    }

    private void sendReply(boolean accept) throws IOException {
        if(hasReplied) {
            throw new IOException("Has already replied");
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        if(accept) {
            out.println(acceptReq);
        } else {
            out.println(denyReq);
        }

        hasReplied = true;
    }

    public void accept() throws IOException {
        sendReply(true);
    }

    public void deny() throws IOException {
        sendReply(false);
    }

    public IncomingRequest(Socket socket) throws Exception {
        this.socket = socket;
        checkValidity();
    }

    private void checkValidity() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String incoming = in.readLine();
        if(!incoming.equals(correctReq)) {
            throw new Exception("Bad request XML");
        }
    }
}
