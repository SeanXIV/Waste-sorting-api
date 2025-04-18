package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingCenter;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends MongoRepository<RecyclingCenter, String> {
    List<RecyclingCenter> findByNameContainingIgnoreCase(String name);
    Page<RecyclingCenter> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<RecyclingCenter> findByAddressContainingIgnoreCase(String address);
    Page<RecyclingCenter> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    List<RecyclingCenter> findByActive(boolean active);
    Page<RecyclingCenter> findByActive(boolean active, Pageable pageable);

    @Query("{ 'acceptedWasteCategories': ?0 }")
    List<RecyclingCenter> findByAcceptedWasteCategory(@Param("wasteCategory") WasteCategory wasteCategory);

    @Query("{ 'acceptedWasteCategories': ?0 }")
    Page<RecyclingCenter> findByAcceptedWasteCategory(@Param("wasteCategory") WasteCategory wasteCategory, Pageable pageable);

    @Query("{ $and: [ " +
           "{ $or: [ { 'name': { $exists: false } }, { 'name': { $regex: ?0, $options: 'i' } } ] }, " +
           "{ $or: [ { 'address': { $exists: false } }, { 'address': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'active': { $exists: false } }, { 'active': ?2 } ] } " +
           "] }")
    List<RecyclingCenter> searchRecyclingCenters(
            @Param("name") String name,
            @Param("address") String address,
            @Param("active") Boolean active);

    @Query("{ $and: [ " +
           "{ $or: [ { 'name': { $exists: false } }, { 'name': { $regex: ?0, $options: 'i' } } ] }, " +
           "{ $or: [ { 'address': { $exists: false } }, { 'address': { $regex: ?1, $options: 'i' } } ] }, " +
           "{ $or: [ { 'active': { $exists: false } }, { 'active': ?2 } ] } " +
           "] }")
    Page<RecyclingCenter> searchRecyclingCenters(
            @Param("name") String name,
            @Param("address") String address,
            @Param("active") Boolean active,
            Pageable pageable);

    // For backward compatibility with controllers still using Long
    default Optional<RecyclingCenter> findById(Long id) {
        return id == null ? Optional.empty() : findById(id.toString());
    }

    default void deleteById(Long id) {
        if (id != null) {
            deleteById(id.toString());
        }
    }
}
