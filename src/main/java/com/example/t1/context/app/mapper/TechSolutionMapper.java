package com.example.t1.context.app.mapper;

import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.model.TechSolution;

import java.util.Optional;

public class TechSolutionMapper {
    public static TechSolution toEntityTechSolution(TechSolutionDTO techSolutionDTO) {
        return TechSolution.builder().name(techSolutionDTO.getName()).
                category(techSolutionDTO.getCategory()).
                documentationUrl(techSolutionDTO.getDocumentationUrl()).build();
    }


}
