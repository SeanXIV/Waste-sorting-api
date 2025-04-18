package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecyclingTipRepository extends JpaRepository<RecyclingTip, Long> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    List<RecyclingTip> findByWasteCategory(WasteCategory wasteCategory);

    List<RecyclingTip> findByTitleContainingIgnoreCase(String title);

    List<RecyclingTip> findByDescriptionContainingIgnoreCase(String description);

    List<RecyclingTip> findBySourceContainingIgnoreCase(String source);

    @Query("SELECT r FROM RecyclingTip r WHERE " +
           "(:wasteCategoryId IS NULL OR r.wasteCategory.id = :wasteCategoryId) AND " +
           "(:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:description IS NULL OR LOWER(r.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
           "(:source IS NULL OR LOWER(r.source) LIKE LOWER(CONCAT('%', :source, '%')))")
    List<RecyclingTip> searchRecyclingTips(
            @Param("wasteCategoryId") Long wasteCategoryId,
            @Param("title") String title,
            @Param("description") String description,
            @Param("source") String source);
}