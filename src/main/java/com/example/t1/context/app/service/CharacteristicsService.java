package com.example.t1.context.app.service;


import com.example.t1.context.app.model.Characteristics;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.repository.CharacteristicsRepository;
import com.example.t1.context.app.repository.TechSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicsService {

    private final CharacteristicsRepository characteristicsRepository;
    private final TechSolutionService techSolutionService;

    @Scheduled(cron = "0 0 0 * * ?")  // Запускается в полночь каждый день
    public void copyTechSolutionDataToCharacteristics() {
        List<TechSolution> techSolutions = techSolutionService.getAllNotNullSolutions();

        for (TechSolution techSolution : techSolutions) {
            Characteristics characteristics = Characteristics.builder()
                    .time(new Date())
                    .usesNum(techSolution.getCurrentUsesNum())
                    .averageScore(techSolution.getCurrentAverageScore())
                    .effectiveness(techSolution.getCurrentEffectiveness())
                    .techSolution(techSolution)
                    .build();

            characteristicsRepository.save(characteristics);
        }
    }

}
