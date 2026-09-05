package com.momentum.domain.repository;

import com.momentum.domain.entity.Sport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {

    Optional<Sport> findByCode(String code);
}
