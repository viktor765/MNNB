package main;

import chat.ChatController;
import chat.HostChatController;
import thread.ChatThread;
import thread.MainModel;
import thread.request.OutgoingRequest;

import java.awt.event.ActionListener;
import java.io.IOException;

public class MainController {
    private ActionListener onConnectButtonClick = e -> {
        ConnectPrompt prompt = new ConnectPrompt();

        try {
            OutgoingRequest outgoingRequest = new OutgoingRequest(prompt.getAddress(), prompt.getPort());

            if(outgoingRequest.isAccepted()) {
                ChatThread chatThread = new ChatThread(outgoingRequest.getSocket());
                new Thread(chatThread).start();
                new ChatController(chatThread);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };

    private MainController() throws IOException {
        int port = new PortPrompt().getPort();
        MainModel mainModel = MainModel.getInstance(5001);
        MainWindow mainWindow = MainWindow.getInstance();
        mainModel.addObserver(mainWindow);
        mainWindow.addConnectButtonListener(onConnectButtonClick);

        HostChatController hostChatController = HostChatController.getInstance(mainModel.getHostChatThread());
    }

    private static MainController instance = null;

    public static MainController getInstance() throws IOException {
        if(instance == null) {
            instance = new MainController();
        }

        return instance;
    }
}
