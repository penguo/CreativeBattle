package com.afordev.creativebattle.Data;

/**
 * Created by penguo on 2018-02-01.
 */

public class ChatData {
    private String name, message;

    public ChatData() {
    }

    public ChatData(String name, String content) {
        this.name = name;
        this.message = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
