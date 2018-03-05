package chat;

import message.Message;
import thread.ChatThread;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ChatPanel extends JPanel implements Observer{
    private final JFrame myFrame;
    private final JButton nameButton;
    private final JButton closeButton;
    private final JButton sendButton;
    private final JButton colorButton;
    private final JTextField messageField;
    private final JTextPane editorPane;
    private final ChatThread chatThread;
    
    private static final int X_SIZE = 400;
    private static final int Y_SIZE = 450;
    private static final int SCROLLPANE_X_SIZE = 350;
    private static final int SCROLLPANE_Y_SIZE = 300;

    public ChatPanel(ChatThread chatThread) {
        this.chatThread = chatThread;
        
        myFrame = new JFrame("Chat, remote server at: " + chatThread.getHostIP());

        editorPane = new JTextPane();
        editorPane.setEditable(false);

        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));

        nameButton = new JButton("Set name");
        colorButton = new JButton("Set color");
        sendButton = new JButton("Send");
        closeButton = new JButton("Close");
        messageField = new JTextField(20);
        
        myFrame.add(this);
                
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(SCROLLPANE_X_SIZE, SCROLLPANE_Y_SIZE));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        
        this.add(editorScrollPane);


        this.add(nameButton);
        this.add(colorButton);
        this.add(messageField);
        this.add(sendButton);
        this.add(closeButton);
        
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object message) {
        if (!(message instanceof Message)) {
            return;
        }

        Message mes = (Message) message; //chatThread.getMessages().get(chatThread.getMessages().size() - 1);
        StyledDocument doc = editorPane.getStyledDocument();
        
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, mes.getColor());

        try {
            if(mes.isDisconnect()) {
                doc.insertString(doc.getLength(), mes.getSender() + " har loggat ut.\n", keyWord);
            } else {
                doc.insertString(doc.getLength(), mes.getSender()
                        + ": " + mes.getText() + "\n", keyWord);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        editorPane.setStyledDocument(doc);
    }
    
    public void addNameListener(ActionListener actionListener) {
        nameButton.addActionListener((ActionEvent e) -> {
            String name = JOptionPane.showInputDialog(myFrame, "Enter name:", null);

            if(name != null) {
                actionListener.actionPerformed(new ActionEvent(colorButton, (int) AWTEvent.TEXT_EVENT_MASK, name));
            }
        });
    }
    
    public void addColorListener(ActionListener actionListener) {
        colorButton.addActionListener((ActionEvent e) -> {
            String colStr = JOptionPane.showInputDialog(myFrame, "Enter color string(hex):", null);

            if(colStr != null) {
                actionListener.actionPerformed(new ActionEvent(colorButton, (int) AWTEvent.TEXT_EVENT_MASK, colStr));
            }
        });
    }

    protected JFrame getMyFrame() {
        return myFrame;
    }

    public void addSendListener(ActionListener actionListener) {
        sendButton.addActionListener(actionListener);
    }
    
    public void addCloseListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }
    
    public String getMessageFieldText() {
        return messageField.getText();
    }

    public void close() {
        myFrame.dispose();
    }
}
