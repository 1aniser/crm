package com.lapuka.crm.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userchatid")
    private User userchat;

    @OneToMany(mappedBy = "chat")
    private Collection<Chatmessage> messages;

    public Chat() {
    }

    public Chat(Long id) {
        this.id = id;
    }

    public Chat(Long id, User userchat) {
        this.id = id;
        this.userchat = userchat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id + '}';
    }
}
