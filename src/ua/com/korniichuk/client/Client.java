package ua.com.korniichuk.client;

import ua.com.korniichuk.ui.UI;
import ua.com.korniichuk.util.MessageCreator;
import ua.com.korniichuk.util.OnlineUsers;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Client implements Runnable {
    private InputStream in;
    private OutputStream out;
    private BufferedReader userInput;
    public static OnlineUsers onlineUsers;
    UI userInterface = new UI();

    public Client() throws IOException {
        Socket socket = new Socket("localhost", 5432);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        userInput = new BufferedReader(new InputStreamReader(System.in));
        Thread listenerThread = new Thread(new Listener());
        listenerThread.start();
    }

    public void sender() throws IOException {
        MessageCreator messageCreator = new MessageCreator();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {

            String nickName = userInput.readLine();
            System.out.println("client :" + nickName);


            if (nickName.equals("")) {
                nickName = Inet4Address.getLocalHost().getHostAddress();
            }
            oos.writeUTF(nickName);
            oos.flush();
            while (true) {
                String text = userInput.readLine();
                Message message = messageCreator.createMessage(nickName, text);
                oos.writeObject(message);
                oos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            sender();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Listener implements Runnable {
        @Override
        public void run() {
            try (ObjectInputStream objectInput = new ObjectInputStream(in)) {
                String introductionMessage = objectInput.readUTF();
                System.out.println(introductionMessage);
                Thread.currentThread().sleep(3000);
                userInterface.outMessage(introductionMessage);
                while (true) {
                    Object object = objectInput.readObject();
                    if (object instanceof Message) {
                        System.out.println("received: " + object);
                        userInterface.outMessage(object.toString());
                    }
                    if (object instanceof OnlineUsers) {

                        onlineUsers = ((OnlineUsers) object);
                        System.out.println("currently online :" + onlineUsers);
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
}
