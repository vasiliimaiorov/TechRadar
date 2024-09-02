package com.example.t1.context.app.repository;

import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TechSolutionRepository extends JpaRepository<TechSolution, Long> {

    Optional<TechSolution> findByName(String name);

    List<TechSolution> findByCategory(Category category);

}
