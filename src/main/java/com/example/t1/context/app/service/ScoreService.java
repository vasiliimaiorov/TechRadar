package com.example.t1.context.app.service;

import com.example.t1.context.app.api.AddScoreRequest;
import com.example.t1.context.app.model.Score;
import com.example.t1.context.app.model.keys.ScoreId;
import com.example.t1.context.app.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final TechSolutionService techSolutionService;

    public List<Score> getUserScores(Long userId) {
        return scoreRepository.findAllByUserId(userId);
    }

    @Transactional
    public Score addOrUpdateScore(Long userId, AddScoreRequest request) {
        var prevScore = scoreRepository.findById(new ScoreId(userId, request.techId()));

        if (prevScore.isEmpty() && techSolutionService.checkPresenceById(request.techId())) {
            var score = Score.builder()
                    .userId(userId)
                    .techSolutionId(request.techId())
                        .scoreValue(request.scoreValue())
                    .build();
            scoreRepository.save(score);
            techSolutionService.handleScoreInsert(score.getTechSolutionId(), score.getScoreValue());
            return score;
        }

        var score = prevScore.get();
        var oldScoreValue = score.getScoreValue();

        score.setScoreValue(request.scoreValue());
        scoreRepository.save(score);
        techSolutionService.handleScoreUpdate(score.getTechSolutionId(), score.getScoreValue(), oldScoreValue);
        return score;
    }

    @Transactional
    public boolean deleteScore(ScoreId id) {
        return scoreRepository.findById(id)
                .map(score -> {
                    scoreRepository.delete(score);
                    techSolutionService.handleScoreDelete(score.getTechSolutionId(), score.getScoreValue());
                    return true;
                })
                .orElse(false);
    }

}
