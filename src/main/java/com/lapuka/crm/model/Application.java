package com.lapuka.crm.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "application")
public class Application{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subject")
    private String subject;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid")
    private User userApl;
    @Column(name = "datecreated")
    private LocalDateTime datecreated;
    @Column(name = "status")
    private String status;

    public Application() {
    }

    public Application(Long id, String subject, String description, User userApl, LocalDateTime date, String status) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.userApl = userApl;
        this.datecreated = date;
        this.status = status;
    }

    public Application(String subject, String description, User userApl, LocalDateTime datecreated, String status) {
        this.subject = subject;
        this.description = description;
        this.userApl = userApl;
        this.datecreated = datecreated;
        this.status = status;
    }

    public User getUserApl() {
        return userApl;
    }

    public void setUserApl(User userApl) {
        this.userApl = userApl;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", userApl=" + userApl +
                ", datecreated=" + datecreated +
                ", status='" + status + '\'' +
                '}';
    }
}
