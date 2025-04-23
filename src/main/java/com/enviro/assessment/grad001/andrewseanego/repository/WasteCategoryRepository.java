package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WasteCategoryRepository extends MongoRepository<WasteCategory, String> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    Optional<WasteCategory> findByNameIgnoreCase(String name);

    List<WasteCategory> findByNameContainingIgnoreCase(String name);
    Page<WasteCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<WasteCategory> findByDescriptionContainingIgnoreCase(String description);
    Page<WasteCategory> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    List<WasteCategory> findByRecyclable(boolean recyclable);
    Page<WasteCategory> findByRecyclable(boolean recyclable, Pageable pageable);

    @Query("{ $and: [ " +
           "{ $or: [ { 'name': { $exists: false } }, { 'name': { $regex: ?0, $options: 'i' } } ] }, " +
           "{ $or: [ { 'description': { $exists: false } }, { 'description': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'recyclable': { $exists: false } }, { 'recyclable': ?2 } ] } " +
           "] }")
    List<WasteCategory> searchWasteCategories(
            @Param("name") String name,
            @Param("description") String description,
            @Param("recyclable") Boolean recyclable);

    @Query("{ $and: [ " +
           "{ $or: [ { 'name': { $exists: false } }, { 'name': { $regex: ?0, $options: 'i' } } ] }, " +
           "{ $or: [ { 'description': { $exists: false } }, { 'description': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'recyclable': { $exists: false } }, { 'recyclable': ?2 } ] } " +
           "] }")
    Page<WasteCategory> searchWasteCategories(
            @Param("name") String name,
            @Param("description") String description,
            @Param("recyclable") Boolean recyclable,
            Pageable pageable);
}
