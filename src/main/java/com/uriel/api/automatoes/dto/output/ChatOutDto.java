package com.uriel.api.automatoes.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ChatOutDto(
        String label,
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime start,
        List<MessageOutDto> messages
) {
}
