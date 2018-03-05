package main;

import thread.MainModel;
import thread.request.IncomingRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JPanel implements Observer {
    private final JFrame frame = new JFrame("Instant Messenger");
    private final JButton connectButton = new JButton("Anslut till Chat");

    private static final int X_SIZE = 200;
    private static final int Y_SIZE = 100;

    public void addConnectButtonListener(ActionListener e) {
        connectButton.addActionListener(e);
    }

    private MainWindow() {
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(this);

        this.add(connectButton);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object message) {
        if(message instanceof IncomingRequest && o instanceof MainModel) {
            prompt((IncomingRequest)message);
        }
    }

    private void prompt(IncomingRequest incomingRequest) {
        int dialogResult = JOptionPane.showConfirmDialog(
                null,
                "Allow " + incomingRequest.getIP() + " to connect?",
                "Incoming connection",
                JOptionPane.YES_NO_OPTION
        );
        if(dialogResult == JOptionPane.YES_OPTION) {
            try {
                MainModel.getInstance(4444).accept(incomingRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You did not accept");
        }
    }

    private static MainWindow instance = null;

    public static MainWindow getInstance() {
        if(instance == null) {
            instance = new MainWindow();
        }

        return instance;
    }
}
