package com.example.t1.context.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDTO {
    TechSolutionDTO techSolution;
    int scoreValue;
}
