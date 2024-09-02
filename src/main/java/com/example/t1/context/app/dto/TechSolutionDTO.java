package com.example.t1.context.app.dto;

import com.example.t1.context.app.model.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechSolutionDTO {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 200)
    @Pattern(regexp = "^[a-zA-ZА-я0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$")
    private String documentationUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

}
