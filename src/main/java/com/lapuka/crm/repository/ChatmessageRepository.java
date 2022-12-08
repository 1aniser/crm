package com.lapuka.crm.repository;

import com.lapuka.crm.model.Chat;
import com.lapuka.crm.model.Chatmessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ChatmessageRepository extends JpaRepository<Chatmessage, Long> {
    ArrayList<Chatmessage> findByIdLike(Long id);

    ArrayList<Chatmessage> findByChatLike(Chat chat);
}
