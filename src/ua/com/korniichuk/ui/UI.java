package ua.com.korniichuk.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        Scene scene = new Scene(root, 700, 400, Color.GHOSTWHITE);
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

        TextField serverAddressField = new TextField();
        serverAddressField.setPrefSize(200, 25);
        serverAddressField.setText("localhost:5432");

        Button connectButton = new Button("Connect");
        connectButton.setPrefSize(100, 20);

        hBoxTopContainer.getChildren().addAll(serverAddressField, connectButton);

        messagesAreaField = new TextArea();

        messagesAreaField.setPrefSize(350, 250);
        messagesAreaField.setEditable(false);


        newMessageField = new TextArea();
        newMessageField.setPrefSize(350, 60);
        vBoxLeftContainer.getChildren().addAll(messagesAreaField, newMessageField);

        activeUsersView = new TextArea();
        activeUsersView.setPrefSize(200, 500);

        vBoxRightContainer.getChildren().add(activeUsersView);

        newMessageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                synchronized (holder) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        holder.add(newMessageField.getText());
                        holder.notifyAll();
                    }
                }
            }
        });


    }

    public void outMessage(String message) {
        UI.messagesAreaField.appendText(message);
        UI.messagesAreaField.appendText(System.getProperty("line.separator"));
    }

    public void updateOnlineUsers(final ArrayList<String> list) {
        activeUsersView.clear();
        for (String element : list) {
            activeUsersView.appendText(element);
            activeUsersView.appendText(System.getProperty("line.separator"));
        }


    }


}
