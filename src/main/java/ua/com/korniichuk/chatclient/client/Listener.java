package ua.com.korniichuk.chatclient.client;

import ua.com.korniichuk.chatclient.ui.Controller;
import ua.com.korniichuk.chatclient.ui.UI;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Listener implements Runnable {
    private InputStream in;
    public static ArrayList<String> activeUsers;
    Controller uiController = new Controller();


    public Listener(Socket socket) throws IOException {
        in = socket.getInputStream();
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInput = new ObjectInputStream(in)) {
            while (true) {
                Object object = objectInput.readObject();
                if (object instanceof ArrayList) {
                    System.out.println("currently online :" + object);
                    activeUsers = (ArrayList<String>) object;
                    uiController.updateOnlineUsers((ArrayList<String>) object);
                } else {
                    if (object instanceof String) {
                        System.out.println(object);
                        uiController.outMessage((String) object);
                    } else {
                        if (object instanceof Message) {
                            System.out.println("received: " + object);
                            uiController.outMessage(object.toString());
                        }else { if (object instanceof Account) {
                           // uiController.outMessage(((Account) object).getExistingNickName());
                            UI.holder.add(((Account) object).getExistingNickName());
                        }
                        }

                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
