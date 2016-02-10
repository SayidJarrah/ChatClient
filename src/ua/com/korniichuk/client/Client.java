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
    public static String inputUImessage;

    public final static Object lock = new Object();


    public Client() throws IOException {
        Socket socket = new Socket("localhost", 5432);
        in = socket.getInputStream();
        out = socket.getOutputStream();
        userInput = new BufferedReader(new InputStreamReader(System.in));
        Thread listenerThread = new Thread(new Listener());
        listenerThread.start();
    }

    public void sender() throws IOException {
        synchronized (UI.holder) {
            MessageCreator messageCreator = new MessageCreator();
            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                synchronized (UI.holder) {}
                while (UI.holder.isEmpty()) {
                    UI.holder.wait();
                }
                String nickName = UI.holder.remove(0);
                System.out.println("client :" + nickName);

                if (nickName.equals("")) {
                    nickName = Inet4Address.getLocalHost().getHostAddress();
                }

                System.out.println("second  - " + inputUImessage);
                oos.writeObject(nickName);
                oos.flush();

                while (true) {
                    String text = userInput.readLine();
                    Message message = messageCreator.createMessage(nickName, text);
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

                    Thread.currentThread().sleep(3000);
                    while (true) {
                        Object object = objectInput.readObject();
                        if (object instanceof OnlineUsers) {
                            onlineUsers = ((OnlineUsers) object);
                            System.out.println("currently online :" + onlineUsers);
                            userInterface.updateOnlineUsers(((OnlineUsers) object).getUsers());
                        } else {
                            if (object instanceof String) {
                                System.out.println(object);
                                userInterface.outMessage(object.toString());
                            } else {
                                if (object instanceof Message) {
                                    System.out.println("received: " + object);
                                    userInterface.outMessage(object.toString());
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


}
