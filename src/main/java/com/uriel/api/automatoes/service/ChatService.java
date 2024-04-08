package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.data.entity.Chat;
import com.uriel.api.automatoes.data.entity.Message;
import com.uriel.api.automatoes.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role.ASSISTANT;
import static org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role.USER;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final OpenAiApi openAiApi = new OpenAiApi(
            System.getenv("AI_BASE_URL"), System.getenv("AI_API_KEY")
    );

    public Chat conversate(String message) {
        return conversate("", message);
    }

    public Chat conversate(String id, String message) {
        var chat = chatRepository.findById(id).orElseGet(this::createChat);

        var userMessage = Message.builder()
                .role(USER)
                .dateTime(LocalDateTime.now())
                .content(message)
                .chat(chat)
                .build();

        var assistantMessage= Message.builder()
                .role(ASSISTANT)
                .chat(chat)
                .build();
        var prevMessages = chat.getMessages().stream().map(this::convertToOpenAiMessage);

        var requestMessage = new OpenAiApi.ChatCompletionMessage(message, USER);
        var response = openAiApi.chatCompletionEntity(new OpenAiApi.ChatCompletionRequest(
                Stream.concat(prevMessages, Stream.of(requestMessage)).toList(),
                "models/mistral-7b-instruct-v0.2.Q4_K_M.gguf", 0.2f, false
        ));
        assistantMessage.setContent(response.getBody().choices().get(0).message().content());
        assistantMessage.setDateTime(LocalDateTime.now());

        chat.getMessages().add(userMessage);
        chat.getMessages().add(assistantMessage);
        chatRepository.save(chat);
        return chat;
    }

    private OpenAiApi.ChatCompletionMessage convertToOpenAiMessage(Message message) {
        return new OpenAiApi.ChatCompletionMessage(message.getContent(), message.getRole());
    }

    private Chat createChat() {
        return Chat.builder()
                .start(LocalDateTime.now())
                .messages(new ArrayList<>())
                .build();
    }

}
