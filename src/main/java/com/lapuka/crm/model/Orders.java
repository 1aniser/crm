package com.lapuka.crm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String description;
    private String user;
    private Date date;
    private String status;

    public Orders() {
    }

    public Orders(Long id, String subject, String description, String user, Date date, String status) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.user = user;
        this.date = date;
        this.status = status;
    }

    public Orders(String subject, String description, String user, Date date, String status) {
        this.subject = subject;
        this.description = description;
        this.user = user;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
