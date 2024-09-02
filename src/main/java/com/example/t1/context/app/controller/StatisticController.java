package com.example.t1.context.app.controller;

import com.example.t1.context.app.model.Characteristics;
import com.example.t1.context.app.service.CharacteristicsService;
import com.example.t1.context.app.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
@PreAuthorize("hasAnyAuthority('VOTER', 'ADMIN')")
@RequiredArgsConstructor
public class StatisticController {

    private final CharacteristicsService characteristicsService;
    private final ScoreService scoreService;

    // TODO remove
    @Operation(summary = "Write techSolution characteristics into DB manually")
    @GetMapping("/characteristics")
    public void snapshotCharacteristics() {
        characteristicsService.copyTechSolutionDataToCharacteristics();
    }

    @GetMapping("/month")
    public ResponseEntity<List<Characteristics>> getMonthStatistic() {
        var statistic = characteristicsService.getCharacteristicForDaysPeriod(30);
        return ResponseEntity.ok(statistic);
    }

    @GetMapping("/year")
    public ResponseEntity<List<Characteristics>> getYearStatistic() {
        var statistic = characteristicsService.getCharacteristicForDaysPeriod(365);
        return ResponseEntity.ok(statistic);
    }

    @GetMapping("/score/distribution/{tech_id}")
    public ResponseEntity<Map<Integer, Integer>> getScoreDistribution(@RequestParam Long techId) {
        var distribution = scoreService.getScoreDistributionForTechnology(techId);
        return ResponseEntity.ok(distribution);
    }
}
