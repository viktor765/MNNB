package main;

import chat.ChatController;
import thread.ChatThread;
import thread.MainModel;

import java.io.IOException;
import java.net.Socket;

public class MainController {
    private MainController() throws IOException {
        MainModel mainModel = MainModel.getInstance(4444);
        MainWindow mainWindow = MainWindow.getInstance();

        mainWindow.addConnectButtonListener(e -> {
            ConnectPrompt prompt = new ConnectPrompt();

            try {
                Socket socket = new Socket(prompt.getAddress(), prompt.getPort());
                ChatThread chatThread = new ChatThread(socket);
                new Thread(chatThread).start();

                new ChatController(chatThread);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private static MainController instance = null;

    public static MainController getInstance() throws IOException {
        if(instance == null) {
            instance = new MainController();
        }

        return instance;
    }
}
