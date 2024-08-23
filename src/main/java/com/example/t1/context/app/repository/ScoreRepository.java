package com.example.t1.context.app.repository;


import com.example.t1.context.app.model.Score;
import com.example.t1.context.app.model.keys.ScoreId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository  extends JpaRepository<Score, ScoreId> {

    List<Score> findAllByUserId(Long userId);

}
