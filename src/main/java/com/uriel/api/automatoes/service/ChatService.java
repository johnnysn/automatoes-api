package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.data.entity.Chat;
import com.uriel.api.automatoes.data.entity.Message;
import com.uriel.api.automatoes.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat conversate(String message) {
        return conversate("", message);
    }

    public Chat conversate(String id, String message) {
        var chat = chatRepository.findById(id).orElseGet(this::createChat);

        var userMessage = Message.builder()
                .role(Message.Role.USER)
                .dateTime(LocalDateTime.now())
                .content(message)
                .chat(chat)
                .build();

        var assistantMessage= Message.builder()
                .role(Message.Role.ASSISTANT)
                .dateTime(LocalDateTime.now())
                .content("Entendi tudo.")
                .chat(chat)
                .build();

        chat.getMessages().add(userMessage);
        chat.getMessages().add(assistantMessage);
        chatRepository.save(chat);
        return chat;
    }

    private Chat createChat() {
        return Chat.builder()
                .start(LocalDateTime.now())
                .messages(new ArrayList<>())
                .build();
    }

}
