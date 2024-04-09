package com.uriel.api.automatoes.dto.input;

import jakarta.validation.constraints.NotBlank;

public record ChatInputDto(
        @NotBlank(message = "Give a label to the chat.")
        String label,
        @NotBlank(message = "Message should not be blank.")
        String message
) { }
