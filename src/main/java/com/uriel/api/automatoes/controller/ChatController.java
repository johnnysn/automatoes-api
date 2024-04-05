package com.uriel.api.automatoes.controller;

import com.uriel.api.automatoes.dto.input.ChatInputDto;
import com.uriel.api.automatoes.dto.output.ChatOutDto;
import com.uriel.api.automatoes.mapper.ChatMapper;
import com.uriel.api.automatoes.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMapper mapper;
    private final ChatService service;

    @PostMapping
    public ChatOutDto conversate(@RequestBody @Valid ChatInputDto inputDto) {
        return mapper.entityToDto(service.conversate(inputDto.id(), inputDto.message()));
    }

}
