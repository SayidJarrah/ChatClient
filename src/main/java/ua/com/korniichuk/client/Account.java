package ua.com.korniichuk.client;

import java.io.Serializable;

public class Account implements Serializable {
    private String existingNickName;

    public String getExistingNickName() {
        return existingNickName;
    }

    public void setExistingNickName(String existingNickName) {
        this.existingNickName = existingNickName;
    }
}
