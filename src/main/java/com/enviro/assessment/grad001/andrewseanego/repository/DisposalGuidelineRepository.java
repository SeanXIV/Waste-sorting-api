package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisposalGuidelineRepository extends MongoRepository<DisposalGuideline, String> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    List<DisposalGuideline> findByWasteCategory(WasteCategory wasteCategory);

    List<DisposalGuideline> findByDescriptionContainingIgnoreCase(String description);

    List<DisposalGuideline> findByStepsContainingIgnoreCase(String steps);

    List<DisposalGuideline> findByLegalRequirementsContainingIgnoreCase(String legalRequirements);

    @Query("{ $and: [ " +
           "{ $or: [ { 'wasteCategory._id': { $exists: false } }, { 'wasteCategory._id': ?0 } ] }, " +
           "{ $or: [ { 'description': { $exists: false } }, { 'description': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'steps': { $exists: false } }, { 'steps': { $regex: ?2, $options: 'i' } } ] }, " +
           "{ $or: [ { 'legalRequirements': { $exists: false } }, { 'legalRequirements': { $regex: ?3, $options: 'i' } } ] } " +
           "] }")
    List<DisposalGuideline> searchDisposalGuidelines(
            @Param("wasteCategoryId") String wasteCategoryId,
            @Param("description") String description,
            @Param("steps") String steps,
            @Param("legalRequirements") String legalRequirements);

    // For backward compatibility with controllers still using Long
    default List<DisposalGuideline> searchDisposalGuidelines(
            Long wasteCategoryId,
            String description,
            String steps,
            String legalRequirements) {
        return searchDisposalGuidelines(
            wasteCategoryId != null ? wasteCategoryId.toString() : null,
            description,
            steps,
            legalRequirements
        );
    }

    // For backward compatibility with controllers still using Long
    default Optional<DisposalGuideline> findById(Long id) {
        return id == null ? Optional.empty() : findById(id.toString());
    }

    default void deleteById(Long id) {
        if (id != null) {
            deleteById(id.toString());
        }
    }
}

