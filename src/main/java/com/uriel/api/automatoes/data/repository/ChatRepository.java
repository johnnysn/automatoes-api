package com.uriel.api.automatoes.data.repository;

import com.uriel.api.automatoes.data.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
