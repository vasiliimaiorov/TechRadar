package com.example.t1.context.app.model;

import com.example.t1.context.app.model.keys.ScoreId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@IdClass(ScoreId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "tech_solution_id")
    private Long techSolutionId;

    @Column(name = "score_value")
    @Min(1)
    @Max(10)
    private int scoreValue;

}