package com.momentum.backend.service;

import com.momentum.backend.dto.SportDto;
import java.util.List;

public interface SportService {

    List<SportDto> getAllSports();

    SportDto createSport(SportDto sportDto);
}
