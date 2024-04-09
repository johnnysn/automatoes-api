package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.data.entity.Chat;
import com.uriel.api.automatoes.data.entity.Message;
import com.uriel.api.automatoes.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final RetrieveCompletionService retrieveCompletionService;

    public Chat conversate(String label, String message) {
        var chat = chatRepository.findByLabel(label).orElse(createChat(label));

        var userMessage = Message.builder()
                .role(Role.USER)
                .dateTime(LocalDateTime.now())
                .content(message)
                .chat(chat)
                .build();
        chat.getMessages().add(userMessage);

        var assistantMessage = retrieveCompletionService.execute(chat.getMessages());

        assistantMessage.setChat(chat);
        chat.getMessages().add(assistantMessage);
        return chatRepository.save(chat);
    }

    private Chat createChat(String label) {
        return Chat.builder()
                .start(LocalDateTime.now())
                .label(label)
                .messages(new ArrayList<>())
                .build();
    }

}
