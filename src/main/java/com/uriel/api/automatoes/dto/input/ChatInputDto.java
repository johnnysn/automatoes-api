package com.uriel.api.automatoes.dto.input;

import jakarta.validation.constraints.NotBlank;

public record ChatInputDto(
        String id,
        @NotBlank(message = "Message should not be blank.")
        String message
) { }
