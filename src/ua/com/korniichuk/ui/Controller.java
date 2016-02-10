package ua.com.korniichuk.ui;


import java.util.ArrayList;

public class Controller {

    public void outMessage(String message) {
        UI.messagesAreaField.appendText(message);
        UI.messagesAreaField.appendText(System.getProperty("line.separator"));
    }

    public void updateOnlineUsers(final ArrayList<String> list) {
        UI.activeUsersView.clear();
        for (String element : list) {
            UI.activeUsersView.appendText(element);
            UI.activeUsersView.appendText(System.getProperty("line.separator"));
        }
    }




}
