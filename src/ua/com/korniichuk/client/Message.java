package ua.com.korniichuk.client;

import java.io.Serializable;

public class Message implements Serializable {
    private String nick;
    private String time;
    private String text;


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(nick).append(" ");
        sb.append("[");
        sb.append(time);
        sb.append("] ");
        sb.append(text);
        return sb.toString();
    }
}
