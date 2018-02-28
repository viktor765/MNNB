import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartPrompt {
    private final boolean server;
    private String address = "127.0.0.1";
    private int port = 4444;
    
    public StartPrompt() {
        JFrame myFrame = new JFrame("Server or Client");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Object[] options = {"Server", "Client"};
        
        int n = JOptionPane.showOptionDialog(
            myFrame,
            "Start as server or client?",
            "",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        server = (n == 0);
        
        if(!server) {
            address = (String)JOptionPane.showInputDialog(
                myFrame,
                "Ange serverns adress:",
                address
            );
        }
        
        String portStr = (String)JOptionPane.showInputDialog(
            myFrame,
            "Ange port:",
            Integer.toString(port)
        );
        
        port = Integer.parseInt(portStr);
    }
    
    public boolean getChoice() {
        return server;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getPort() {
        return port;
    }
}