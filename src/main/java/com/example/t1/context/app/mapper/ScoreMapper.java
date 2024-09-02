package com.example.t1.context.app.mapper;

import com.example.t1.context.app.dto.ScoreDTO;
import com.example.t1.context.app.model.Score;

public class ScoreMapper {

    public static ScoreDTO toDto(Score score) {
        return ScoreDTO.builder()
                .techSolution(TechSolutionMapper.toTechSolutionDto(score.getTechSolution()))
                .scoreValue(score.getScoreValue())
                .build();
    }
}
