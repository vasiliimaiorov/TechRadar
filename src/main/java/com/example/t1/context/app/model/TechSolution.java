package com.example.t1.context.app.model;

import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "tech_solution")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_solution_id")
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 1, max = 100)
    private String name;

    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @Column(name = "documentation_url")
    @Size(max = 200)
    private String documentationUrl;

    @Min(0)
    @Max(15)
    @Column(name = "current_effectiveness")
    private Double currentEffectiveness;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "techSolution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores;

    @OneToMany(mappedBy = "techSolution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Characteristics> characteristics;


    @PastOrPresent
    @Column(name = "last_effectiveness_update")
    private LocalDateTime lastEffectivenessUpdate;
}
