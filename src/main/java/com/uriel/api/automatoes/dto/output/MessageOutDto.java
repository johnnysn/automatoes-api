package com.uriel.api.automatoes.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionMessage.Role;

import java.time.LocalDateTime;

public record MessageOutDto(
        Role role,
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dateTime,
        String content
) {
}
