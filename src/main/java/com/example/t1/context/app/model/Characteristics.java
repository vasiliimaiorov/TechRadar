package com.example.t1.context.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "characteristics_in_time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Characteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @PastOrPresent
    @Column(nullable = false, unique = true)
    private LocalDateTime time;

    @PositiveOrZero
    @Column(name = "uses_num")
    private Integer usesNum;

    @Min(0)
    @Max(10)
    @Column(name = "average_score")
    private Double averageScore;

    @Min(0)
    @Max(15)
    private Double effectiveness;

    @ManyToOne
    @JoinColumn(name="tech_solution_id")
    private TechSolution techSolution;
}
