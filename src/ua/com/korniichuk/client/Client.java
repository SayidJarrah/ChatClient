package ua.com.korniichuk.client;

import ua.com.korniichuk.util.MessageCreator;
import ua.com.korniichuk.util.OnlineUsers;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Client implements Runnable {
    private InputStream in;
    private OutputStream out;
    private BufferedReader userInput;


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
                System.out.println(objectInput.readUTF());
                while (true) {
                    Object object = objectInput.readObject();
                    if (object instanceof Message) {
                        System.out.println("received: " + object);
                    }
                    if (object instanceof OnlineUsers) {
                        System.out.println("currently online :" + object);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}
