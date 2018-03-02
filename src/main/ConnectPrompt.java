package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectPrompt {
    private String address = "127.0.0.1";
    private int port = 4444;
    
    public ConnectPrompt() {
        JFrame myFrame = new JFrame("Connect to server");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        address = (String)JOptionPane.showInputDialog(
            myFrame,
            "Enter server address:",
            address
        );
        
        String portStr = (String)JOptionPane.showInputDialog(
            myFrame,
            "Enter port:",
            Integer.toString(port)
        );
        
        port = Integer.parseInt(portStr);
    }

    public String getAddress() {
        return address;
    }
    
    public int getPort() {
        return port;
    }
}