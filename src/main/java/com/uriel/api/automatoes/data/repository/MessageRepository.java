package com.uriel.api.automatoes.data.repository;

import com.uriel.api.automatoes.data.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
