package com.example.t1.context.app.controller;


import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.service.TechSolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.t1.context.app.model.TechSolution;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tech")
public class TechSolutionController {

    private final TechSolutionService techSolutionService;

    @PostMapping("createSolution")
    public ResponseEntity<Long> createSolution(@Valid @RequestBody TechSolutionDTO techSolutionDTO){
        return ResponseEntity.ok(techSolutionService.createTechSolution(techSolutionDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTechSolution(@Valid @PathVariable Long id) {
        boolean isDeleted = techSolutionService.deleteTechSolution(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAllSolutions")
    public ResponseEntity<List> getAllTechSolution() {

        List<TechSolution> solutions = techSolutionService.getAllTechSolutions();

        if (solutions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(solutions, HttpStatus.OK);
        }
    }


    @GetMapping("getByCategory/{category}")
    public ResponseEntity<List<TechSolution>> getTechSolutionsByCategory(@Valid @PathVariable Category category) {
        try {

            List<TechSolution> solutions = techSolutionService.getTechSolutionsByCategory(category);

            if (solutions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(solutions, HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("updateTechSolution")
    public ResponseEntity<Void> updateTechSolution(@Valid @RequestBody TechSolution updatedTechSolution) {
        boolean isUpdated = techSolutionService.updateTechSolution(updatedTechSolution);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
