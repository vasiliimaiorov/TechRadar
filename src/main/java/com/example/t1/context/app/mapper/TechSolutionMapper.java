package com.example.t1.context.app.mapper;

import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.dto.TechSolutionOnRadarDTO;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.service.RangeCheckerService;
import com.example.t1.context.app.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechSolutionMapper {

    private static final RangeCheckerService rangeCheckerService = new RangeCheckerService();
    private final ScoreService scoreService;

    public static TechSolution toEntityTechSolution(TechSolutionDTO techSolutionDTO) {
        return TechSolution.builder().name(techSolutionDTO.getName()).
                category(techSolutionDTO.getCategory()).
                documentationUrl(techSolutionDTO.getDocumentationUrl()).build();
    }

    public static TechSolutionDTO toTechSolutionDto(TechSolution techSolution) {
        return TechSolutionDTO.builder()
                .id(techSolution.getId())
                .name(techSolution.getName())
                .category(techSolution.getCategory())
                .build();
    }

    public TechSolutionOnRadarDTO toTechSolutionOnRadar(TechSolution techSolution) {
        TechSolutionOnRadarDTO.TechSolutionOnRadarDTOBuilder builder = TechSolutionOnRadarDTO.builder()
                .label(techSolution.getName())
                .quadrant(techSolution.getCategory().ordinal())
                .ring(rangeCheckerService.determineRange(techSolution.getCurrentEffectiveness()))
                .id(techSolution.getId())
                .moved(techSolution.getStatus().getNum());

        if (techSolution.getDocumentationUrl() != null) {
            builder.link(techSolution.getDocumentationUrl());
        }

        builder.active(scoreService.getUsesNumberForTechnology(techSolution.getId())!=0);

        return builder.build();
    }


}
