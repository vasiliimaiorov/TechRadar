package com.example.t1.context.app.controller;

import com.example.t1.context.app.api.AddScoreRequest;
import com.example.t1.context.app.api.DeleteScoreRequest;
import com.example.t1.context.app.api.TechSolutionOnRadarResponse;
import com.example.t1.context.app.dto.ScoreDTO;
import com.example.t1.context.app.dto.TechSolutionOnRadarDTO;
import com.example.t1.context.app.mapper.ScoreMapper;
import com.example.t1.context.app.mapper.TechSolutionMapper;
import com.example.t1.context.app.model.User;
import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.keys.ScoreId;
import com.example.t1.context.app.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/score")
@PreAuthorize("hasAnyAuthority('VOTER', 'ADMIN')")
@RequiredArgsConstructor
public class ScoreController {

    private final TechSolutionMapper techSolutionMapper;
    private final ScoreService scoreService;

    @GetMapping("/getScoresByTechSolutionsCategory")
    public ResponseEntity<List<ScoreDTO>> getScoresByTechSolutionsCategory(@RequestParam @Valid Category category) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var scores = scoreService.getUserScoresByTechSolutionCategory(user.getId(), category);

        var scoresDto = scores.stream()
                .map(ScoreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(scoresDto);
    }

    @GetMapping("/getScores")
    public ResponseEntity<List<ScoreDTO>> getScores() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var scores = scoreService.getUserScores(user.getId());

        var scoresDto = scores.stream()
                .map(ScoreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(scoresDto);
    }

    @GetMapping("getNotScoredTechSolutionsByCategory/{category}")
    public ResponseEntity<TechSolutionOnRadarResponse> getTechSolutionsByCategory(@Valid @PathVariable Category category) {
        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<TechSolutionOnRadarDTO> solutions = scoreService.getNotScoredTechSolutionsByCategory(user.getId(), category)
                    .stream()
                    .map(techSolutionMapper::toTechSolutionOnRadar)
                    .collect(Collectors.toList());
            TechSolutionOnRadarResponse response = new TechSolutionOnRadarResponse(solutions);

            if (solutions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<?> addOrUpdateUserScore(@RequestBody @Valid AddScoreRequest request) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        var score = scoreService.addOrUpdateScore(user, request);
        if (score != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ScoreMapper.toDto(score));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Technology Solution id not found");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserScore(@RequestBody @Valid DeleteScoreRequest request) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var scoreId = new ScoreId(user.getId(), request.techId());

        if (scoreService.deleteScore(scoreId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
