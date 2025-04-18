package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WasteCollectionRepository extends MongoRepository<WasteCollection, String> {
    List<WasteCollection> findByWasteCategory(WasteCategory wasteCategory);
    Page<WasteCollection> findByWasteCategory(WasteCategory wasteCategory, Pageable pageable);

    List<WasteCollection> findByLocationContainingIgnoreCase(String location);
    Page<WasteCollection> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    List<WasteCollection> findByStatus(String status);
    Page<WasteCollection> findByStatus(String status, Pageable pageable);

    List<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Page<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("{ $and: [ " +
           "{ $or: [ { 'wasteCategory._id': { $exists: false } }, { 'wasteCategory._id': ?0 } ] }, " +
           "{ $or: [ { 'location': { $exists: false } }, { 'location': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'status': { $exists: false } }, { 'status': ?2 } ] }, " +
           "{ $or: [ { 'collectionDate': { $exists: false } }, { 'collectionDate': { $gte: ?3 } } ] }, " +
           "{ $or: [ { 'collectionDate': { $exists: false } }, { 'collectionDate': { $lte: ?4 } } ] } " +
           "] }")
    List<WasteCollection> searchWasteCollections(
            @Param("wasteCategoryId") String wasteCategoryId,
            @Param("location") String location,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("{ $and: [ " +
           "{ $or: [ { 'wasteCategory._id': { $exists: false } }, { 'wasteCategory._id': ?0 } ] }, " +
           "{ $or: [ { 'location': { $exists: false } }, { 'location': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'status': { $exists: false } }, { 'status': ?2 } ] }, " +
           "{ $or: [ { 'collectionDate': { $exists: false } }, { 'collectionDate': { $gte: ?3 } } ] }, " +
           "{ $or: [ { 'collectionDate': { $exists: false } }, { 'collectionDate': { $lte: ?4 } } ] } " +
           "] }")
    Page<WasteCollection> searchWasteCollections(
            @Param("wasteCategoryId") String wasteCategoryId,
            @Param("location") String location,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
