import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.text.*;

public class ChatPanel extends JPanel implements Observer{
    private final JFrame myFrame;
    private final JButton nameButton;
    private final JButton closeButton;
    private final JButton sendButton;
    private final JButton colorButton;
    private final JTextField messageField;
    private final JTextPane editorPane;
    private final ChatThread chatThread;
    
    private static final int X_SIZE = 800;  
    private static final int Y_SIZE = 600;
    private static final int SCROLLPANE_X_SIZE = 700;
    private static final int SCROLLPANE_Y_SIZE = 500;

    public ChatPanel(ChatThread chatThread) {
        this.chatThread = chatThread;
        
        myFrame = new JFrame("wIM");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                       
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
        Message mes = (Message) message; //chatThread.getMessages().get(chatThread.getMessages().size() - 1);
        StyledDocument doc = editorPane.getStyledDocument();
        
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, mes.getColor());

        try {
            if (mes.isDisconnect()) {
                doc.insertString(doc.getLength(), mes.getSender() + " har loggat ut.\n", keyWord);
            } else {
                doc.insertString(doc.getLength(), mes.getSender()
                        + ": " + mes.getText() + "\n", keyWord);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        editorPane.setStyledDocument(doc);
    }
    
    public void addNameListener(ActionListener actionListener) {
        nameButton.addActionListener((ActionEvent e) -> {
            String name = JOptionPane.showInputDialog(myFrame, "Ange namn:", null);

            if(name != null) {
                actionListener.actionPerformed(new ActionEvent(colorButton, (int) AWTEvent.TEXT_EVENT_MASK, name));
            }
        });
    }
    
    public void addColorListener(ActionListener actionListener) {
        colorButton.addActionListener((ActionEvent e) -> {
            String colStr = JOptionPane.showInputDialog(myFrame, "Ange färgsträng(hex):", null);

            if(colStr != null) {
                actionListener.actionPerformed(new ActionEvent(colorButton, (int) AWTEvent.TEXT_EVENT_MASK, colStr));
            }
        });
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
