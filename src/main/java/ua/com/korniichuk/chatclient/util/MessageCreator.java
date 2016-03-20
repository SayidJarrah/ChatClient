package ua.com.korniichuk.chatclient.util;

import ua.com.korniichuk.chatclient.client.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageCreator {

    public Message createMessage(String nick, String text) {
        Message message = new Message();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        message.setNick(nick);
        message.setTime(format.format(new Date()));
        message.setText(text);
        return message;
    }
}
