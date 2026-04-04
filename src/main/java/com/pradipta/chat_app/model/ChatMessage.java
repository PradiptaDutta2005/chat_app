package com.pradipta.chat_app.model;

public class ChatMessage {

    private String sender;
    private String receiver;
    private String content;

    public ChatMessage() {}

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}