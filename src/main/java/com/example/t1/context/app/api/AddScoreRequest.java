package com.example.t1.context.app.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddScoreRequest(@NotNull Long techId,
                              @Min(1) @Max(10) int scoreValue) {}
