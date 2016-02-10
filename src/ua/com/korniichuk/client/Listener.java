package ua.com.korniichuk.client;

import ua.com.korniichuk.ui.Controller;
import ua.com.korniichuk.util.OnlineUsers;

import java.io.*;
import java.net.Socket;


public class Listener implements Runnable {
    private InputStream in;
    public static OnlineUsers onlineUsers;
    Controller uiController = new Controller();


    public Listener(Socket socket) throws IOException {
        in = socket.getInputStream();
    }
    @Override
    public void run() {

        try (ObjectInputStream objectInput = new ObjectInputStream(in)) {

            Thread.currentThread().sleep(3000);
            while (true) {
                Object object = objectInput.readObject();
                if (object instanceof OnlineUsers) {
                    onlineUsers = ((OnlineUsers) object);
                    System.out.println("currently online :" + onlineUsers);
                    uiController.updateOnlineUsers(((OnlineUsers) object).getUsers());
                } else {
                    if (object instanceof String) {
                        System.out.println(object);
                        uiController.outMessage((String) object);
                    } else {
                        if (object instanceof Message) {
                            System.out.println("received: " + object);
                            uiController.outMessage(object.toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
