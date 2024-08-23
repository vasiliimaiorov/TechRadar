package com.example.t1.context.app.controller;

import com.example.t1.context.app.api.AddScoreRequest;
import com.example.t1.context.app.api.DeleteScoreRequest;
import com.example.t1.context.app.model.Score;
import com.example.t1.context.app.model.User;
import com.example.t1.context.app.model.keys.ScoreId;
import com.example.t1.context.app.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@PreAuthorize("hasAuthority('VOTER')")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping()
    public ResponseEntity<List<Score>> getUserScores() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(scoreService.getUserScores(user.getId()));
    }

    @PostMapping()
    public ResponseEntity<Score> addOrUpdateUserScore(@RequestBody @Valid AddScoreRequest request) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        var score = scoreService.addOrUpdateScore(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(score);
    }

    @DeleteMapping()
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
