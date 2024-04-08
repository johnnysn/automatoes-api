package com.uriel.api.automatoes.dto.output;

import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role;

public record MessageOutDto(
        Role role,
        String content
) {
}
