package ua.com.korniichuk.start;


import javafx.application.Application;
import javafx.stage.Stage;
import ua.com.korniichuk.client.Sender;
import ua.com.korniichuk.client.Listener;
import ua.com.korniichuk.ui.UI;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Starter extends Application {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5432);
        Sender sender = new Sender(socket);
        Listener listener = new Listener(socket);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(listener);
        executorService.submit(sender);

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UI userInterface = new UI();
        primaryStage.setTitle("ClientChat v.1.0");
        userInterface.initUI(primaryStage);
        primaryStage.show();

    }
}
