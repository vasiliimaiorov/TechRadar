package com.example.t1.context.app.api;

import jakarta.validation.constraints.NotNull;

public record DeleteScoreRequest(@NotNull Long techId) {}
