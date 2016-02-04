package ua.com.dkorniichuk.client;

import java.io.*;
import java.net.Socket;

public class Client {
    BufferedReader inputMessages;
    BufferedWriter outputMessages;
    BufferedReader userInput;


    public Client() throws IOException {
        Socket socket = new Socket("localhost", 5432);
            inputMessages = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputMessages = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));
            Thread listenerThread = new Thread(new Listener());
            listenerThread.start();

    }

    public void sender() throws IOException {
        while (true) {
            String userString = userInput.readLine();
            outputMessages.write(userString);
            outputMessages.write("\n");
            outputMessages.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.sender();
    }


    private class Listener implements Runnable {

        @Override
        public void run() {
            String line = null;
            try {
                while ((line = inputMessages.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }


}
