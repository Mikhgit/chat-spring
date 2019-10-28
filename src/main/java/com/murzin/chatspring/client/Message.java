package com.murzin.chatspring.client;

public class Message {

    private long id;
    private String text;
    private String user;
    private Iterable<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Iterable<Message> getMessages() {
        return messages;
    }

    public void setMessages(Iterable<Message> messages) {
        this.messages = messages;
    }
}
