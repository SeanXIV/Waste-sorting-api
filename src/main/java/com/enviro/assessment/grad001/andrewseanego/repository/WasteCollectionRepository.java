package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WasteCollectionRepository extends JpaRepository<WasteCollection, Long> {
    List<WasteCollection> findByWasteCategory(WasteCategory wasteCategory);
    Page<WasteCollection> findByWasteCategory(WasteCategory wasteCategory, Pageable pageable);
    
    List<WasteCollection> findByLocationContainingIgnoreCase(String location);
    Page<WasteCollection> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    
    List<WasteCollection> findByStatus(String status);
    Page<WasteCollection> findByStatus(String status, Pageable pageable);
    
    List<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Page<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    @Query("SELECT wc FROM WasteCollection wc WHERE " +
           "(:wasteCategoryId IS NULL OR wc.wasteCategory.id = :wasteCategoryId) AND " +
           "(:location IS NULL OR LOWER(wc.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:status IS NULL OR wc.status = :status) AND " +
           "(:startDate IS NULL OR wc.collectionDate >= :startDate) AND " +
           "(:endDate IS NULL OR wc.collectionDate <= :endDate)")
    List<WasteCollection> searchWasteCollections(
            @Param("wasteCategoryId") Long wasteCategoryId,
            @Param("location") String location,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
            
    @Query("SELECT wc FROM WasteCollection wc WHERE " +
           "(:wasteCategoryId IS NULL OR wc.wasteCategory.id = :wasteCategoryId) AND " +
           "(:location IS NULL OR LOWER(wc.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:status IS NULL OR wc.status = :status) AND " +
           "(:startDate IS NULL OR wc.collectionDate >= :startDate) AND " +
           "(:endDate IS NULL OR wc.collectionDate <= :endDate)")
    Page<WasteCollection> searchWasteCollections(
            @Param("wasteCategoryId") Long wasteCategoryId,
            @Param("location") String location,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
