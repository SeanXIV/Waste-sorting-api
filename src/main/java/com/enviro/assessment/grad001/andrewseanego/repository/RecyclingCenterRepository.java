package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingCenter;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {
    List<RecyclingCenter> findByNameContainingIgnoreCase(String name);
    Page<RecyclingCenter> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    List<RecyclingCenter> findByAddressContainingIgnoreCase(String address);
    Page<RecyclingCenter> findByAddressContainingIgnoreCase(String address, Pageable pageable);
    
    List<RecyclingCenter> findByActive(boolean active);
    Page<RecyclingCenter> findByActive(boolean active, Pageable pageable);
    
    @Query("SELECT rc FROM RecyclingCenter rc JOIN rc.acceptedWasteCategories wc WHERE wc = :wasteCategory")
    List<RecyclingCenter> findByAcceptedWasteCategory(@Param("wasteCategory") WasteCategory wasteCategory);
    
    @Query("SELECT rc FROM RecyclingCenter rc JOIN rc.acceptedWasteCategories wc WHERE wc = :wasteCategory")
    Page<RecyclingCenter> findByAcceptedWasteCategory(@Param("wasteCategory") WasteCategory wasteCategory, Pageable pageable);
    
    @Query("SELECT rc FROM RecyclingCenter rc WHERE " +
           "(:name IS NULL OR LOWER(rc.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:address IS NULL OR LOWER(rc.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
           "(:active IS NULL OR rc.active = :active)")
    List<RecyclingCenter> searchRecyclingCenters(
            @Param("name") String name,
            @Param("address") String address,
            @Param("active") Boolean active);
            
    @Query("SELECT rc FROM RecyclingCenter rc WHERE " +
           "(:name IS NULL OR LOWER(rc.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:address IS NULL OR LOWER(rc.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
           "(:active IS NULL OR rc.active = :active)")
    Page<RecyclingCenter> searchRecyclingCenters(
            @Param("name") String name,
            @Param("address") String address,
            @Param("active") Boolean active,
            Pageable pageable);
}
