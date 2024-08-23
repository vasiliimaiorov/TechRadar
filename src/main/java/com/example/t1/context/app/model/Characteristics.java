package com.example.t1.context.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

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

    @Column(nullable = false)
    private Date time;


    @Column(name = "uses_num")
    private Integer usesNum;

    @Min(1)
    @Max(10)
    @Column(name = "average_score")
    private Double averageScore;

    @Min(1)
    @Max(15)
    private Double effectiveness ;

    @ManyToOne
    @JoinColumn(name="tech_solution_id")
    private TechSolution techSolution;
}
