public class Frame {
    public static void main(String[] args) {
        ChatThread ct = new ChatThread(null);

        ChatController c = new ChatController(ct);
    }
}
