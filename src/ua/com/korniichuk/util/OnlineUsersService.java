package ua.com.korniichuk.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class OnlineUsersService {
    Socket socket;

    public OnlineUsersService(Socket socket) {
        this.socket = socket;
    }

    public void receiveActiveUsers() {
        OnlineUsers onlineUsers = new OnlineUsers();
        try(InputStream inputStream = socket.getInputStream()){
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            while (true){
                onlineUsers.setUsers((ArrayList<String>) ois.readObject());
                System.out.println(onlineUsers.getUsers());
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
