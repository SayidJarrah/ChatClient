package ua.com.korniichuk.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Dima on 05.02.2016.
 */
public class UI {

    public static void initUI(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 400, Color.GHOSTWHITE);
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
        serverAddressField.setPrefSize(200,25);
        serverAddressField.setText("localhost:5432");

        Button connectButton = new Button("Connect");
        connectButton.setPrefSize(100,20);

        hBoxTopContainer.getChildren().addAll(serverAddressField,connectButton);

        TextField messagesAreaField = new TextField();
        messagesAreaField.setPrefSize(350,250);
        messagesAreaField.setEditable(false);

        TextField newMessageField = new TextField();
        newMessageField.setPrefSize(350,60);
        vBoxLeftContainer.getChildren().addAll(messagesAreaField,newMessageField);

    //    ObservableList<String> usersOnline = FXCollections.observableArrayList(
      //  ListView<String> usersOnlineList = new ListView<>()

       // vBoxRightContainer

    }
}
