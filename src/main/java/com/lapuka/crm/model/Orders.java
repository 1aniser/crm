package com.lapuka.crm.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders implements Builder{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subject")
    private String subject;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid")
    private User user;
    @Column(name = "datecreated")
    private LocalDateTime datecreated;
    @Column(name = "dateconfirmed")
    private LocalDateTime dateconfirmed;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private Integer price;

    public Orders() {
    }

    public Orders(Long id, String subject, String description, User user, LocalDateTime datecreated, LocalDateTime dateconfirmed, String status, Integer price) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.user = user;
        this.datecreated = datecreated;
        this.dateconfirmed = dateconfirmed;
        this.status = status;
        this.price = price;
    }

    public Orders(String subject, String description, User user, LocalDateTime datecreated, LocalDateTime dateconfirmed, String status, Integer price) {
        this.subject = subject;
        this.description = description;
        this.user = user;
        this.datecreated = datecreated;
        this.dateconfirmed = dateconfirmed;
        this.status = status;
        this.price = price;
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

    public LocalDateTime getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(LocalDateTime datecreated) {
        this.datecreated = datecreated;
    }

    public LocalDateTime getDateconfirmed() {
        return dateconfirmed;
    }

    public void setDateconfirmed(LocalDateTime dateconfirmed) {
        this.dateconfirmed = dateconfirmed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", datecreated=" + datecreated +
                ", dateconfirmed=" + dateconfirmed +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}
