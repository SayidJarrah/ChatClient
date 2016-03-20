package ua.com.korniichuk.chatclient.start;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.com.korniichuk.chatclient.client.Sender;
import ua.com.korniichuk.chatclient.client.Listener;
import ua.com.korniichuk.chatclient.ui.UI;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Starter extends Application {
    private static Socket socket;

    public static void main(String[] args) {
        launch(args);

    }

    public static void launcher(String host, int port) throws IOException {
        socket = new Socket(host, port);
        Sender sender = new Sender(socket);
        Listener listener = new Listener(socket);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(listener);
        executorService.submit(sender);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UI userInterface = new UI();
        primaryStage.setTitle("ClientChat v.1.0");
        userInterface.initUI(primaryStage);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                try {
                    socket.shutdownInput();
                    socket.shutdownOutput();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    System.exit(0);
                }
            }
        });
        primaryStage.show();
    }

    public static Socket getSocket() {
        return socket;
    }
}
