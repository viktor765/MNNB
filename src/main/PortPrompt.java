package main;

import javax.swing.*;

public class PortPrompt {
    private int port = 4444;

    public PortPrompt() {
        JFrame myFrame = new JFrame("Listen to port");

        String portStr = (String)JOptionPane.showInputDialog(
                myFrame,
                "Enter port:",
                Integer.toString(port)
        );

        port = Integer.parseInt(portStr);
    }

    public int getPort() {
        return port;
    }
}
