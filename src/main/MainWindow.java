package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JPanel {
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

    private static MainWindow instance = null;

    public static MainWindow getInstance() {
        if(instance == null) {
            instance = new MainWindow();
        }

        return instance;
    }
}
