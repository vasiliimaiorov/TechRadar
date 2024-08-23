package com.example.t1.context.app.model;

import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tech_solution")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_solution_id")
    private Long id;

    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @Column(unique = true, nullable = false)
    @Size(min = 1, max = 100)
    private String name;

    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @Column(name = "documentation_url", unique = true)
    @Size(min = 2, max = 200)
    private String documentationUrl;

    @Min(1)
    @Max(10)
    @Column(name = "current_uses_num")
    private Integer currentUsesNum;

    @Min(1)
    @Max(10)
    @Column(name = "current_average_score")
    private Double currentAverageScore;

    @Min(1)
    @Max(15)
    @Column(name = "current_effectiveness")
    private Double currentEffectiveness;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status")
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

}
