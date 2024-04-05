package com.uriel.api.automatoes.mapper;

import com.uriel.api.automatoes.data.entity.Chat;
import com.uriel.api.automatoes.dto.output.ChatOutDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatOutDto entityToDto(Chat chat);

}
