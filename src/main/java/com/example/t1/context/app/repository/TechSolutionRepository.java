package com.example.t1.context.app.repository;

import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface TechSolutionRepository extends JpaRepository<TechSolution, Long> {

    Optional<TechSolution> findByName(String name);

    List<TechSolution> findByCategory(Category category);

    default Optional<Integer> findMaxUses() {
        return findAll().stream()
                .map(TechSolution::getCurrentUsesNum)
                .max(Comparator.naturalOrder());
    }

    @Query("SELECT t FROM TechSolution t WHERE t.currentAverageScore IS NOT NULL AND t.currentEffectiveness IS NOT NULL AND t.currentUsesNum IS NOT NULL")
    List<TechSolution> findAllWhereFieldsNotNull();

}
