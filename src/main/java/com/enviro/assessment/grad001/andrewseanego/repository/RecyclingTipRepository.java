package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecyclingTipRepository extends JpaRepository<RecyclingTip, Long> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database


    // add custom query methods here if needed

}