package com.example.t1.context.app.service;

import com.example.t1.context.app.api.AddScoreRequest;
import com.example.t1.context.app.model.Score;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.model.User;
import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.keys.ScoreId;
import com.example.t1.context.app.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final TechSolutionService techSolutionService;

    public List<Score> getUserScores(Long userId) {
        return scoreRepository.findAllByUserId(userId);
    }

    public List<Score> getUserScoresByTechSolutionCategory(Long userId, Category category) {
        List<Score> scores = scoreRepository.findAllByUserId(userId);
        List<Score> scoresByCategory = new ArrayList<>();
        for (var score: scores) {
            if (score.getTechSolution().getCategory() == category) {
                scoresByCategory.add(score);
            }
        }
        return scoresByCategory;
    }

    @Transactional
    public Score addOrUpdateScore(User user, AddScoreRequest request) {
        var techSolution = techSolutionService.getTechSolutionById(request.techId());

        if (techSolution.isPresent()) {
            var scoreId = new ScoreId(user.getId(), request.techId());
            var prevScore = scoreRepository.findById(scoreId);

            if (prevScore.isEmpty()) {
                var score = Score.builder()
                        .id(scoreId)
                        .user(user)
                        .techSolution(techSolution.get())
                        .scoreValue(request.scoreValue())
                        .build();
                scoreRepository.save(score);
                return score;
            }

            var score = prevScore.get();

            score.setScoreValue(request.scoreValue());
            scoreRepository.save(score);
            return score;
        }
        return null;
    }

    public List<TechSolution> getNotScoredTechSolutionsByCategory(Long userId, Category category) {
        List<TechSolution> solutionsByCategory = techSolutionService.getTechSolutionsByCategory(category);
        List<TechSolution> notScoredSolutions = new ArrayList<>();

        for (var solution: solutionsByCategory) {
            if (!checkIfUserScoredTechSolution(userId, solution.getId())) {
                notScoredSolutions.add(solution);
            }
        }
        return notScoredSolutions;
    }

    @Transactional
    public boolean deleteScore(ScoreId id) {
        return scoreRepository.findById(id)
                .map(score -> {
                    scoreRepository.delete(score);
                    return true;
                })
                .orElse(false);
    }

    public Map<Integer, Integer> getScoreDistributionForTechnology(Long techId) {
        Map<Integer, Integer> scoresDistribution = new HashMap<>();
        for(int i = 0; i <= 10; i++) {
            var scoresNum = scoreRepository.getTechnologyUsesNumByScoreValue(techId, i);
            scoresDistribution.put(i, scoresNum);
        }
        return scoresDistribution;
    }

    public Integer getUsesNumberForTechnology(Long techId) {
        return scoreRepository.getTechnologyUsesNumByTechId(techId);
    }

    public Integer getSumScoreForTechnology(Long techId) {
        return scoreRepository.scoreSumByTechId(techId);
    }

    public Integer findMaxUsesInCategory(List<Long> categoryIds) {
        List<Long> counts = scoreRepository.findAllUsesNumForTechByCategory(categoryIds);
        return counts.isEmpty() ? 0 : counts.get(0).intValue();
    }

    public boolean checkIfUserScoredTechSolution(Long userId, Long techSolutionId) {
        return scoreRepository.findById(new ScoreId(userId, techSolutionId)).isPresent();
    }


}
