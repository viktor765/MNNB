package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("130.237.226.34", 5001);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("hej heeeej");
    }
}
