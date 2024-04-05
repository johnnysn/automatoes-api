package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.data.entity.Chat;
import com.uriel.api.automatoes.data.entity.Message;
import com.uriel.api.automatoes.data.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public record ChatService(ChatRepository chatRepository) {

    public Chat conversate(String id, String message) {
        var chat = chatRepository.findById(id).orElseGet(this::createChat);

        var userMessage = Message.builder()
                .id(UUID.randomUUID().toString())
                .role(Message.Role.USER)
                .content(message)
                .chat(chat)
                .build();

        var assistantMessage= Message.builder()
                .id(UUID.randomUUID().toString())
                .role(Message.Role.ASSISTANT)
                .content("Entendi tudo.")
                .chat(chat)
                .build();

        chat.getMessages().add(userMessage);
        chat.getMessages().add(assistantMessage);
        return chatRepository.save(chat);
    }

    private Chat createChat() {
        return Chat.builder().id(UUID.randomUUID().toString())
                .start(LocalDateTime.now())
                .build();
    }

}
