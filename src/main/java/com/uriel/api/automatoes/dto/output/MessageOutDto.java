package com.uriel.api.automatoes.dto.output;

import com.uriel.api.automatoes.data.entity.Message;

public record MessageOutDto(
        Message.Role role,
        String content
) {
}
