package com.lapuka.crm.model;

import javax.persistence.*;

@Entity
@Table(name = "chatmessage")
public class Chatmessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chatid")
    private Chat chat;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="senderid")
    private User sender;
    @Column(name = "message")
    private String message;

    public Chatmessage() {
    }

    public Chatmessage(Long id, Chat chat, User sender, String message) {
        this.id = id;
        this.chat = chat;
        this.sender = sender;
        this.message = message;
    }

    public Chatmessage(Chat chat, User sender, String message) {
        this.chat = chat;
        this.sender = sender;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChatid() {
        return chat;
    }

    public void setChatid(Chat chat) {
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Chatmessage{" +
                "id=" + id +
                ", chatid=" + chat +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                '}';
    }
}
