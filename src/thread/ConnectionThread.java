package thread;

import message.Message;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

public class ConnectionThread extends Observable implements Runnable {
    private Socket socket;
    private final PrintWriter out;

    public void sendString(String str) {
        out.println(str);
    }

    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    public void disconnect() {
        //ev skicka ett meddelande
        done = true;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean done = false;

    @Override
    public void run() {
        BufferedReader in;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(!done) {
            try {
                String received = in.readLine();

                if(received == null) {
                    done = true;
                } else {
                    Message message = new Message(received);//XMLParser

                    setChanged();
                    notifyObservers(message);
                }
            } catch (SocketException e) {
                done = true;

                System.out.println("Socket closed");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("readLine failed");
                e.printStackTrace();
            } catch (SAXException e) {
                System.out.println("Badly formated message");
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        done = true;

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionThread(Socket socket) throws IOException {
        this.socket = socket;

        out = new PrintWriter(socket.getOutputStream(), true);
    }
}
