package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecyclingTipRepository extends MongoRepository<RecyclingTip, String> {
    // The JpaRepository interface already provides a set of CRUD methods for interacting with the database

    // Search methods
    List<RecyclingTip> findByWasteCategory(WasteCategory wasteCategory);

    List<RecyclingTip> findByTitleContainingIgnoreCase(String title);

    List<RecyclingTip> findByDescriptionContainingIgnoreCase(String description);

    List<RecyclingTip> findBySourceContainingIgnoreCase(String source);

    @Query("{ $and: [ " +
           "{ $or: [ { 'wasteCategory._id': { $exists: false } }, { 'wasteCategory._id': ?0 } ] }, " +
           "{ $or: [ { 'title': { $exists: false } }, { 'title': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'description': { $exists: false } }, { 'description': { $regex: ?2, $options: 'i' } } ] }, " +
           "{ $or: [ { 'source': { $exists: false } }, { 'source': { $regex: ?3, $options: 'i' } } ] } " +
           "] }")
    List<RecyclingTip> searchRecyclingTips(
            @Param("wasteCategoryId") String wasteCategoryId,
            @Param("title") String title,
            @Param("description") String description,
            @Param("source") String source);

    // For backward compatibility with controllers still using Long
    default List<RecyclingTip> searchRecyclingTips(
            Long wasteCategoryId,
            String title,
            String description,
            String source) {
        return searchRecyclingTips(
            wasteCategoryId != null ? wasteCategoryId.toString() : null,
            title,
            description,
            source
        );
    }
}