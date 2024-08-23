package com.example.t1.context.app.dto;

import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechSolutionDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    @Column(unique = true, nullable = false)
    @Size(min = 1, max = 100)
    private String name;

    @Column(unique = true)
    @Size(min = 2, max = 200)
    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    private String documentationUrl;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private Category category;

}
