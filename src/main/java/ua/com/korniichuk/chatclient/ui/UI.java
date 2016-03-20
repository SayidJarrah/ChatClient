package ua.com.korniichuk.chatclient.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ua.com.korniichuk.chatclient.start.Starter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UI {
    static TextArea messagesAreaField;
    static TextArea newMessageField;
    static TextArea activeUsersView;
    public final static List<String> holder = new ArrayList<>();


    public void initUI(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 550, 400, Color.GHOSTWHITE);
        stage.setScene(scene);


        VBox vBoxLeftContainer = new VBox();
        vBoxLeftContainer.setPadding(new Insets(10));
        vBoxLeftContainer.setSpacing(10);
        root.setLeft(vBoxLeftContainer);

        VBox vBoxRightContainer = new VBox();
        vBoxRightContainer.setPadding(new Insets(10));
        vBoxRightContainer.setSpacing(10);
        root.setRight(vBoxRightContainer);

        HBox hBoxTopContainer = new HBox();
        hBoxTopContainer.setPadding(new Insets(10));
        hBoxTopContainer.setSpacing(10);
        root.setTop(hBoxTopContainer);

        final TextField serverAddressField = new TextField();
        serverAddressField.setPrefSize(290, 25);
        serverAddressField.setText("localhost:5432");

        Button connectButton = new Button("Connect");
        connectButton.setPrefSize(100, 20);

        Button disconnectButton = new Button("Disconnect");
        disconnectButton.setPrefSize(100,20);


        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newMessageField.setDisable(false);
                if (serverAddressField.getText().equals(null)||
                        serverAddressField.getText().isEmpty()||
                        !serverAddressField.getText().contains(":")){
                    serverAddressField.setText("Enter correct server address!!!");
                }
                else{
                    String[] serverAddress = serverAddressField.getText().split(":");
                    try {
                        Starter.launcher(serverAddress[0], Integer.parseInt(serverAddress[1]));
                        connectButton.setDisable(true);
                        disconnectButton.setDisable(false);
                    } catch (IOException e) {
                        System.out.println("connection trouble");
                        Stage errorMessageStage = new Stage();
                        errorMessageStage.setTitle("Connection troubles");
                        errorMessageStage.show();
                        BorderPane errorMessageRoot = new BorderPane();
                        Scene errorMessageScene = new Scene(errorMessageRoot,270,60,Color.YELLOWGREEN);
                        errorMessageStage.setScene(errorMessageScene);
                        Label errorMessageLabel = new Label("Server isn't available. Try again later...");
                        errorMessageRoot.setCenter(errorMessageLabel);
                    }

                }
            }
        });

        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connectButton.setDisable(false);
                try {
                    Starter.getSocket().shutdownOutput();
                    Starter.getSocket().shutdownInput();
                    newMessageField.setDisable(true);
                    activeUsersView.clear();
                    disconnectButton.setDisable(true);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        hBoxTopContainer.getChildren().addAll(serverAddressField, connectButton,disconnectButton);

        messagesAreaField = new TextArea();

        messagesAreaField.setPrefSize(400, 250);
        messagesAreaField.setEditable(false);
        messagesAreaField.setFont(Font.font("Verdana",14));


        newMessageField = new TextArea();
        newMessageField.setPrefSize(350, 60);
        vBoxLeftContainer.getChildren().addAll(messagesAreaField, newMessageField);

        activeUsersView = new TextArea();
        activeUsersView.setPrefSize(100, 290);
        activeUsersView.setEditable(false);
        activeUsersView.setFont(Font.font("Verdana",14));
        activeUsersView.setPadding(new Insets(10));

        Label activeUsersLabel = new Label("Users online");
        activeUsersLabel.setFont(Font.font("Verdana",16));


        vBoxRightContainer.getChildren().addAll(activeUsersLabel, activeUsersView);

        newMessageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                synchronized (holder) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        holder.add(newMessageField.getText());
                        newMessageField.clear();
                        holder.notifyAll();
                    }
                }
            }
        });


    }

}
