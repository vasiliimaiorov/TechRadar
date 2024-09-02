package com.example.t1.context.app.repository;

import com.example.t1.context.app.model.Characteristics;
import com.example.t1.context.app.model.TechSolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CharacteristicsRepository extends JpaRepository<Characteristics, Long> {

    List<Characteristics> findByTechSolution(TechSolution techSolution);

    List<Characteristics> findByTimeAfter(LocalDateTime time);

}
