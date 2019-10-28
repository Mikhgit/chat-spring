package com.murzin.chatspring.server.dbService.dataSet;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "Text", nullable = false)
    private String text;

    @Column(name = "User", nullable = false)
    private String user;

    @Transient
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
