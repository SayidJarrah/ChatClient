package ua.com.korniichuk.client;

import ua.com.korniichuk.ui.UI;
import ua.com.korniichuk.util.MessageCreator;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Sender implements Runnable {

    private OutputStream out;
    private final String CLIENT_IP = Inet4Address.getLocalHost().getHostAddress();


    public Sender(Socket socket) throws IOException {
        out = socket.getOutputStream();
    }

    public void sender() throws IOException {
        synchronized (UI.holder) {
            MessageCreator messageCreator = new MessageCreator();
            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(CLIENT_IP);
                while (UI.holder.isEmpty()) {
                    UI.holder.wait();
                }

                String nickName = UI.holder.remove(0);
                System.out.println("client :" + nickName);

                if (nickName.equals("")) {
                    nickName = CLIENT_IP;
                }
                oos.writeObject(nickName);
                oos.flush();

                while (true) {
                    while (UI.holder.isEmpty()) {
                        UI.holder.wait();
                    }
                    String text = UI.holder.remove(0);
                    Message message = messageCreator.createMessage(nickName, text);
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try {
            sender();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
