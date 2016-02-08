package ua.com.korniichuk.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dima on 05.02.2016.
 */
public class OnlineUsers implements Serializable {
    ArrayList<String> users;

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OnlineUsers{");
        sb.append("users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
