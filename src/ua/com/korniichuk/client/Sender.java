package ua.com.korniichuk.client;

import ua.com.korniichuk.ui.UI;
import ua.com.korniichuk.util.MessageCreator;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;

public class Sender implements Runnable {

    private OutputStream out;
    private BufferedReader userInput;



    public Sender(Socket socket) throws IOException {
        out = socket.getOutputStream();
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void sender() throws IOException {
        synchronized (UI.holder) {
            MessageCreator messageCreator = new MessageCreator();
            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {

                while (UI.holder.isEmpty()) {
                    UI.holder.wait();
                }
                String nickName = UI.holder.remove(0);
                System.out.println("client :" + nickName);

                if (nickName.equals("")) {
                    nickName = Inet4Address.getLocalHost().getHostAddress();
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




}