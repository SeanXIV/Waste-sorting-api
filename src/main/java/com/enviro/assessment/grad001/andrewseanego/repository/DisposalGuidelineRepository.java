package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisposalGuidelineRepository extends JpaRepository<DisposalGuideline, Long> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    List<DisposalGuideline> findByWasteCategory(WasteCategory wasteCategory);

    List<DisposalGuideline> findByDescriptionContainingIgnoreCase(String description);

    List<DisposalGuideline> findByStepsContainingIgnoreCase(String steps);

    List<DisposalGuideline> findByLegalRequirementsContainingIgnoreCase(String legalRequirements);

    @Query("SELECT d FROM DisposalGuideline d WHERE " +
           "(:wasteCategoryId IS NULL OR d.wasteCategory.id = :wasteCategoryId) AND " +
           "(:description IS NULL OR LOWER(d.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
           "(:steps IS NULL OR LOWER(d.steps) LIKE LOWER(CONCAT('%', :steps, '%'))) AND " +
           "(:legalRequirements IS NULL OR LOWER(d.legalRequirements) LIKE LOWER(CONCAT('%', :legalRequirements, '%')))")
    List<DisposalGuideline> searchDisposalGuidelines(
            @Param("wasteCategoryId") Long wasteCategoryId,
            @Param("description") String description,
            @Param("steps") String steps,
            @Param("legalRequirements") String legalRequirements);
}

