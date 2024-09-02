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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    @EmbeddedId
    private ScoreId id;

    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "tech_solution_id")
    @MapsId("techSolutionId")
    @ManyToOne
    private TechSolution techSolution;

    @Column(name = "score_value")
    @Min(1)
    @Max(10)
    private int scoreValue;

}