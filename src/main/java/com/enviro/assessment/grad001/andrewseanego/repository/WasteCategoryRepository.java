package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    List<WasteCategory> findByNameContainingIgnoreCase(String name);
    Page<WasteCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<WasteCategory> findByDescriptionContainingIgnoreCase(String description);
    Page<WasteCategory> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    List<WasteCategory> findByRecyclable(boolean recyclable);
    Page<WasteCategory> findByRecyclable(boolean recyclable, Pageable pageable);

    @Query("SELECT w FROM WasteCategory w WHERE " +
           "(:name IS NULL OR LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:description IS NULL OR LOWER(w.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
           "(:recyclable IS NULL OR w.recyclable = :recyclable)")
    List<WasteCategory> searchWasteCategories(
            @Param("name") String name,
            @Param("description") String description,
            @Param("recyclable") Boolean recyclable);

    @Query("SELECT w FROM WasteCategory w WHERE " +
           "(:name IS NULL OR LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:description IS NULL OR LOWER(w.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
           "(:recyclable IS NULL OR w.recyclable = :recyclable)")
    Page<WasteCategory> searchWasteCategories(
            @Param("name") String name,
            @Param("description") String description,
            @Param("recyclable") Boolean recyclable,
            Pageable pageable);
}
