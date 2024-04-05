package com.uriel.api.automatoes.dto.output;

import java.util.List;

public record ChatOutDto(
        String id,
        List<MessageOutDto> messages
) {
}
