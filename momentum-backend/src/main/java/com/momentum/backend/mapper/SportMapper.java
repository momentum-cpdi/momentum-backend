package com.momentum.backend.mapper;

import com.momentum.backend.dto.SportDto;
import com.momentum.domain.entity.Sport;
import org.springframework.stereotype.Component;

@Component
public class SportMapper {

    public SportDto toDto(Sport sport) {
        return new SportDto(
            sport.getId(),
            sport.getName(),
            sport.getCode(),
            sport.getType(),
            sport.isActive()
        );
    }

    public Sport toEntity(SportDto sportDto) {
        Sport sport = new Sport();
        sport.setId(sportDto.id());
        sport.setName(sportDto.name());
        sport.setCode(sportDto.code());
        sport.setType(sportDto.type());
        sport.setActive(sportDto.active());
        return sport;
    }
}
