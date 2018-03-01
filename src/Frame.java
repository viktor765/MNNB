import java.io.IOException;

public class Frame {
    public static void main(String[] args) {
        ChatThread ct = null;
        try {
            ct = new ChatThread(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChatController c = new ChatController(ct);
    }
}
