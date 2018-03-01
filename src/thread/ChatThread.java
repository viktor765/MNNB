package thread;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

public class ChatThread extends Observable implements Runnable {
    private Socket socket;
    private final PrintWriter out;

    private String name = "player";
    private Color color = Color.GREEN;

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String colStr) {
        this.color = new Color(Integer.parseInt(colStr, 16));
    }

    public void sendMessage(String msg) {
        out.println(new Message(msg, name, color).toXML());
    }

    public void disconnect() {
        out.println("<message sender=\"" + name + "\"><disconnect/></message>");
        done = true;
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
            socket.close();//räcker med bara denna?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatThread(Socket socket) throws IOException {
        this.socket = socket;

        out = new PrintWriter(socket.getOutputStream(), true);
    }
}
