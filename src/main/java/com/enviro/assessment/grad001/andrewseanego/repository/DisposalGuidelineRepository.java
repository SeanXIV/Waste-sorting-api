package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisposalGuidelineRepository extends JpaRepository<DisposalGuideline, Long> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database
}

