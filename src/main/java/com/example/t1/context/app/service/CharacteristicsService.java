package com.example.t1.context.app.service;

import com.example.t1.context.app.model.Characteristics;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.repository.CharacteristicsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicsService {

    private final CharacteristicsRepository characteristicsRepository;
    private final TechSolutionService techSolutionService;

    @Scheduled(cron = "@monthly")
    public void copyTechSolutionDataToCharacteristics() {
        List<TechSolution> techSolutions = techSolutionService.getAllNotNullSolutions();

        for (TechSolution techSolution : techSolutions) {
            Characteristics characteristics = Characteristics.builder()
                    .time(LocalDateTime.now())
                    .effectiveness(techSolution.getCurrentEffectiveness())
                    .techSolution(techSolution)
                    .build();

            characteristicsRepository.save(characteristics);
        }
    }

    public List<Characteristics> getCharacteristicForDaysPeriod(int daysNum) {
        LocalDateTime lastDate = LocalDateTime.now().minusDays(daysNum);
        return characteristicsRepository.findByTimeAfter(lastDate);
    }

}
