package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.configuration.AiConfiguration;
import com.uriel.api.automatoes.data.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
class RetrieveCompletionLLExecutor implements RetrieveCompletionExecutor {

    private final OpenAiApi openAiApi;
    private final AiConfiguration aiConfiguration;

    public Message execute(List<Message> messages) {
        var response = openAiApi.chatCompletionEntity(new OpenAiApi.ChatCompletionRequest(
                messages.stream().map(this::convertToOpenAiMessage).toList(),
                aiConfiguration.getChatModel(), aiConfiguration.getChatTemperature(), false
        ));

        if (response.getBody() == null || response.getBody().choices().isEmpty())
            throw new RuntimeException("No response from AI model.");

        return Message.builder()
                .role(Message.Role.ASSISTANT)
                .content(response.getBody().choices().get(0).message().content())
                .dateTime(LocalDateTime.now())
                .build();
    }

    private OpenAiApi.ChatCompletionMessage convertToOpenAiMessage(Message message) {
        return new OpenAiApi.ChatCompletionMessage(
                message.getContent(), OpenAiApi.ChatCompletionMessage.Role.valueOf(message.getRole().name())
        );
    }

}
