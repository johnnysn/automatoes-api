package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.configuration.AiConfiguration;
import com.uriel.api.automatoes.data.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class RetrieveCompletionService {

    private final OpenAiApi openAiApi;
    private final AiConfiguration aiConfiguration;

    Message execute(List<Message> messages) {
        var response = openAiApi.chatCompletionEntity(new OpenAiApi.ChatCompletionRequest(
                messages.stream().map(this::convertToOpenAiMessage).toList(),
                aiConfiguration.getChatModel(), aiConfiguration.getChatTemperature(), false
        ));

        if (response.getBody() == null || response.getBody().choices().isEmpty())
            throw new RuntimeException("No response from AI model.");

        return Message.builder()
                .role(Role.ASSISTANT)
                .content(response.getBody().choices().get(0).message().content())
                .dateTime(LocalDateTime.now())
                .build();
    }

    private OpenAiApi.ChatCompletionMessage convertToOpenAiMessage(Message message) {
        return new OpenAiApi.ChatCompletionMessage(message.getContent(), message.getRole());
    }

}
