package com.example.socialmedia;

public class ChatMessage_Model {

    private String sender;
    private String receiver;
    private String content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
