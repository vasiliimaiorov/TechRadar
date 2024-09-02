package com.example.t1.context.app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechSolutionOnRadarDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String link;

    @Min(0)
    @Max(3)
    @NotNull
    private Integer quadrant;

    @Min(0)
    @Max(3)
    @NotNull
    private Integer ring;

    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 200)
    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @NotBlank
    private String label;

    private boolean active;

    @Min(-1)
    @Max(2)
    @NotNull
    private Integer moved;

}
