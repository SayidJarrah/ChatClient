package ua.com.korniichuk.start;


import javafx.application.Application;
import javafx.stage.Stage;
import ua.com.korniichuk.client.Client;
import ua.com.korniichuk.ui.UI;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Starter extends Application {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(client);
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
