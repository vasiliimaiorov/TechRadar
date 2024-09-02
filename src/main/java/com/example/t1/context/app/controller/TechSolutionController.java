package com.example.t1.context.app.controller;

import com.example.t1.api.exeptions.InvalidUrlException;
import com.example.t1.context.app.api.TechSolutionOnRadarResponse;
import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.dto.TechSolutionOnRadarDTO;
import com.example.t1.context.app.mapper.TechSolutionMapper;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.service.EffectivenessService;
import com.example.t1.context.app.service.TechSolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tech")
public class TechSolutionController {
    private final TechSolutionMapper techSolutionMapper;

    private final TechSolutionService techSolutionService;
    private final EffectivenessService effectivenessService;

    @PostMapping("createSolution")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> createSolution(@Valid @RequestBody TechSolutionDTO techSolutionDTO) throws Exception {
        try {
            return ResponseEntity.ok(techSolutionService.createTechSolution(techSolutionDTO).toString());
        }
        catch (InvalidUrlException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteTechSolution(@Valid @PathVariable Long id) {
        boolean isDeleted = techSolutionService.deleteTechSolution(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAllSolutions")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VOTER')")
    public ResponseEntity<TechSolutionOnRadarResponse> getAllTechSolution() {

        List<TechSolutionOnRadarDTO> solutions = techSolutionService.getAllTechSolutions().stream()
                .map(techSolutionMapper::toTechSolutionOnRadar)
                .collect(Collectors.toList());

        TechSolutionOnRadarResponse response = new TechSolutionOnRadarResponse(solutions);
        if (solutions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("updateTechSolution")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> updateTechSolution(@Valid @RequestBody TechSolution updatedTechSolution) throws Exception {
        try {
            boolean isUpdated = techSolutionService.updateTechSolution(updatedTechSolution);
            if (isUpdated) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (InvalidUrlException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/updateEffectivenessOnClick")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updateEffectivenessOnClick() {
        effectivenessService.updateEffectiveness();
        effectivenessService.postponeScheduledMethod();
    }

}
