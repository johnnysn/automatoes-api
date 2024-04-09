package com.uriel.api.automatoes.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uriel.api.automatoes.data.entity.Message;

import java.time.LocalDateTime;

public record MessageOutDto(
        Message.Role role,
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime dateTime,
        String content
) {
}
