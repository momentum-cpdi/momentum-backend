package com.momentum.backend.service;

import com.momentum.backend.dto.SportDto;
import com.momentum.backend.entity.Sport;
import com.momentum.backend.repository.SportRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SportDto> getAllSports() {
        return sportRepository.findAll().stream()
            .map(sport -> new SportDto(
                sport.getId(),
                sport.getName(),
                sport.getCode(),
                sport.getType(),
                sport.isActive()
            ))
            .toList();
    }

    @Override
    @Transactional
    public SportDto createSport(SportDto sportDto) {
        if (sportRepository.findByCode(sportDto.code()).isPresent()) {
            throw new IllegalArgumentException("Un sport avec ce code existe déjà.");
        }

        Sport sport = new Sport();
        sport.setName(sportDto.name());
        sport.setCode(sportDto.code());
        sport.setType(sportDto.type());
        sport.setActive(sportDto.active());

        Sport savedSport = sportRepository.save(sport);
        return new SportDto(
            savedSport.getId(),
            savedSport.getName(),
            savedSport.getCode(),
            savedSport.getType(),
            savedSport.isActive()
        );
    }
}
