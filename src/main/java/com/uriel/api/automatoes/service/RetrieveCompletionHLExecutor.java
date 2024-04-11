package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.configuration.AiConfiguration;
import com.uriel.api.automatoes.data.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.ChatMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RetrieveCompletionHLExecutor implements RetrieveCompletionExecutor {

    private final AiConfiguration aiConfiguration;
    private final OpenAiChatClient chatClient;
    @Override
    public Message execute(List<Message> messages) {
        var openAiMessages = messages.stream().map(this::convertToOpenAiMessage).toList();

        var response = chatClient.call(
                new Prompt(
                        openAiMessages,
                        OpenAiChatOptions.builder()
                                .withModel(aiConfiguration.getChatModel())
                                .withTemperature(aiConfiguration.getChatTemperature())
                                .withFunction("weatherFunction")
                                .build()
                )
        );
        var result = response.getResult();

        return Message.builder()
                .role(Message.Role.ASSISTANT)
                .content(result.getOutput().getContent())
                .dateTime(LocalDateTime.now())
                .build();
    }

    private org.springframework.ai.chat.messages.Message convertToOpenAiMessage(Message message) {
        return new ChatMessage(MessageType.valueOf(message.getRole().name()), message.getContent());
    }
}
