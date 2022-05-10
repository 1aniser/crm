package com.lapuka.crm.model;

import java.time.LocalDateTime;

public interface Builder {

    public void setId(Long id);

    public void setSubject(String subject);

    public void setDescription(String description);

    public void setDatecreated(LocalDateTime datecreated);

    public void setDateconfirmed(LocalDateTime dateconfirmed);

    public void setStatus(String status);

    public void setPrice(Integer price);
    public void setUser(User user);
}
