package com.example.t1.context.app.service;

import com.example.t1.context.app.dto.TechSolutionOnRadarDTO;
import com.example.t1.context.app.mapper.TechSolutionMapper;
import com.example.t1.context.app.model.TechSolution;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EffectivenessService {
    private final TechSolutionService techSolutionService;
    private final ScoreService scoreService;

    private boolean postponed = false;

    @Scheduled(cron = "@monthly")
    public void scheduledMethod() {
        if (!postponed) {
            updateEffectiveness();
        } else {
            postponed = false; // Сброс состояния отложенности
        }
    }

    public void updateEffectiveness() {
        List<TechSolution> techSolutions = techSolutionService.getAllNotNullSolutions();

        for (TechSolution techSolution : techSolutions) {
            Integer maxUses = scoreService.findMaxUsesInCategory(techSolutionService.getTechSolutionsIdByCategory(techSolution.getCategory()));
            maxUses = (maxUses == null) || (maxUses == 0)? 1 : maxUses;
            Integer scoreSum = scoreService.getSumScoreForTechnology(techSolution.getId());
            Integer usesNum = scoreService.getUsesNumberForTechnology(techSolution.getId());
            if (usesNum != 0 && usesNum != null) {
                double newEffectiveness = (double) scoreSum / usesNum + 5 * ((double) usesNum / maxUses);
                newEffectiveness = Double.isNaN(newEffectiveness) ? 0 : newEffectiveness;

                if (newEffectiveness < 0 || newEffectiveness > 15) {
                    throw new IllegalArgumentException("Calculated effectiveness(" + newEffectiveness + ") is out of valid range");
                }
                techSolutionService.setNewStatus(techSolution, newEffectiveness);
                techSolution.setCurrentEffectiveness(newEffectiveness);
            }
            techSolution.setLastEffectivenessUpdate(LocalDateTime.now());
            techSolutionService.updateFields(techSolution);

        }
        }


    public void postponeScheduledMethod() {
        postponed = true;
    }



}
