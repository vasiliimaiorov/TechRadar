package com.example.t1.context.app.service;

import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.mapper.TechSolutionMapper;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.enums.Status;
import com.example.t1.context.app.repository.TechSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechSolutionService {

    private final TechSolutionRepository techSolutionRepository;
    private final RangeCheckerService rangeCheckerService;

    public Long createTechSolution(TechSolutionDTO techSolutionDTO){

        TechSolution techSolution = TechSolutionMapper.toEntityTechSolution(techSolutionDTO);
        techSolution.setCurrentAverageScore(1.0);
        techSolution.setCurrentEffectiveness(1.0);
        techSolution.setCurrentUsesNum(0);
        techSolutionRepository.save(techSolution);
        return techSolutionRepository.findByName(techSolution.getName()).get().getId();
    }

    public boolean deleteTechSolution(Long techSolutionId) {
        return techSolutionRepository.findById(techSolutionId)
                .map(techSolution -> {
                    techSolutionRepository.delete(techSolution);
                    return true;
                })
                .orElse(false);
    }

    public List<TechSolution> getAllTechSolutions(){
        return techSolutionRepository.findAll();
    }

    public boolean updateTechSolution(TechSolution updatedTechSolution) {
        return techSolutionRepository.findById(updatedTechSolution.getId())
                .map(existingTechSolution -> {
                    existingTechSolution.setName(updatedTechSolution.getName());
                    existingTechSolution.setDocumentationUrl(updatedTechSolution.getDocumentationUrl());
                    existingTechSolution.setCategory(updatedTechSolution.getCategory());
                    techSolutionRepository.save(existingTechSolution);
                    return true;
                })
                .orElse(false);
    }

    public List<TechSolution> getTechSolutionsByCategory(Category category) {
        return techSolutionRepository.findByCategory(category);
    }


    public void handleScoreInsert(Long techSolutionId, Integer newScore) {
        TechSolution techSolution = techSolutionRepository.findById(techSolutionId)
                .orElseThrow(() -> new RuntimeException("TechSolution not found with id: " + techSolutionId));
        Double newScoreSum = techSolution.getCurrentAverageScore() * techSolution.getCurrentUsesNum() + newScore;
        Integer usesNum = techSolution.getCurrentUsesNum() + 1;
        updateFields(techSolution, newScoreSum, usesNum);
    }


    public void handleScoreUpdate(Long techSolutionId, Integer newScore, Integer oldScore) {
        TechSolution techSolution = techSolutionRepository.findById(techSolutionId)
                .orElseThrow(() -> new RuntimeException("TechSolution not found with id: " + techSolutionId));
        if (newScore == null || newScore < 1 || newScore > 10) {
            throw new IllegalArgumentException("Score must be between 1 and 10.");
        }

        Double newScoreSum = techSolution.getCurrentAverageScore() * techSolution.getCurrentUsesNum() + newScore - oldScore;
        Integer usesNum = techSolution.getCurrentUsesNum();
        updateFields(techSolution, newScoreSum, usesNum);

    }

    public void handleScoreDelete(Long techSolutionId, Integer oldScore) {
        TechSolution techSolution = techSolutionRepository.findById(techSolutionId)
                .orElseThrow(() -> new RuntimeException("TechSolution not found with id: " + techSolutionId));
        Double newScoreSum = techSolution.getCurrentAverageScore() * techSolution.getCurrentUsesNum() + oldScore;
        Integer usesNum = techSolution.getCurrentUsesNum()-1;
        updateFields(techSolution, newScoreSum, usesNum);
    }



    private void updateFields(TechSolution techSolution, Double newScoreSum, Integer usesNum) {
        Double newAvgScore = newScoreSum/usesNum;
        Integer maxUses = techSolutionRepository.findMaxUses().orElse(1);
        Double newEffectiveness = newAvgScore + 5 * (usesNum/maxUses);

        var oldRingNum = rangeCheckerService.determineRange(techSolution.getCurrentEffectiveness());
        var newRingNum = rangeCheckerService.determineRange(newEffectiveness);
        var difference = oldRingNum - newRingNum;

        if (difference > 0) {
            techSolution.setStatus(Status.MOVED_UP);
        } else if (difference < 0) {
            techSolution.setStatus(Status.MOVED_DOWN);
        }

        if (newEffectiveness < 1 || newEffectiveness > 15) {
            throw new IllegalArgumentException("Calculated effectiveness is out of valid range.");
        }

        if (newAvgScore < 1 || newAvgScore > 10) {
            throw new IllegalArgumentException("Calculated average score is out of valid range.");
        }
        techSolution.setCurrentAverageScore(newAvgScore);

        techSolution.setCurrentEffectiveness(newEffectiveness);
        techSolution.setCurrentUsesNum(usesNum);
        techSolutionRepository.save(techSolution);
    }

    public boolean checkPresenceById(Long techSolutionId) {
        return techSolutionRepository.findById(techSolutionId).isPresent();
    }

    public List<TechSolution> getAllNotNullSolutions(){
        return techSolutionRepository.findAllWhereFieldsNotNull();
    }


}
