package com.example.t1.context.app.repository;


import com.example.t1.context.app.model.Score;
import com.example.t1.context.app.model.keys.ScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository  extends JpaRepository<Score, ScoreId> {

    List<Score> findAllByUserId(Long userId);

    @Query("SELECT COUNT(*) FROM Score s WHERE s.techSolution.id = ?1 AND s.scoreValue = ?2")
    Integer getTechnologyUsesNumByScoreValue(Long techSolutionId, int scoreValue);

    @Query("SELECT COUNT(*) FROM Score WHERE techSolution.id = ?1")
    Integer getTechnologyUsesNumByTechId(Long techSolutionId);

    @Query("SELECT SUM(scoreValue) FROM Score WHERE techSolution.id = ?1")
    Integer scoreSumByTechId(Long techSolutionId);

    @Query("SELECT COUNT(s) FROM Score s WHERE s.techSolution.id IN :categoryIds GROUP BY s.techSolution.id ORDER BY COUNT(s) DESC")
    List<Long> findAllUsesNumForTechByCategory(@Param("categoryIds") List<Long> categoryIds);






}
