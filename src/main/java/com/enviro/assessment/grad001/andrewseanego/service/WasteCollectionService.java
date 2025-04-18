package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCollection;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WasteCollectionService {
    private final WasteCollectionRepository wasteCollectionRepository;

    @Autowired
    public WasteCollectionService(WasteCollectionRepository wasteCollectionRepository) {
        this.wasteCollectionRepository = wasteCollectionRepository;
    }

    public List<WasteCollection> getAllWasteCollections() {
        return wasteCollectionRepository.findAll();
    }

    public Page<WasteCollection> getAllWasteCollections(Pageable pageable) {
        return wasteCollectionRepository.findAll(pageable);
    }

    public Optional<WasteCollection> getWasteCollectionById(String id) {
        return wasteCollectionRepository.findById(id);
    }

    // For backward compatibility with controllers still using Long
    public Optional<WasteCollection> getWasteCollectionById(Long id) {
        return wasteCollectionRepository.findById(id.toString());
    }

    public WasteCollection saveWasteCollection(WasteCollection wasteCollection) {
        return wasteCollectionRepository.save(wasteCollection);
    }

    public void deleteWasteCollection(String id) {
        wasteCollectionRepository.deleteById(id);
    }

    // For backward compatibility with controllers still using Long
    public void deleteWasteCollection(Long id) {
        wasteCollectionRepository.deleteById(id.toString());
    }

    // Search methods
    public List<WasteCollection> findByWasteCategory(WasteCategory wasteCategory) {
        return wasteCollectionRepository.findByWasteCategory(wasteCategory);
    }

    public Page<WasteCollection> findByWasteCategory(WasteCategory wasteCategory, Pageable pageable) {
        return wasteCollectionRepository.findByWasteCategory(wasteCategory, pageable);
    }

    public List<WasteCollection> findByLocation(String location) {
        return wasteCollectionRepository.findByLocationContainingIgnoreCase(location);
    }

    public Page<WasteCollection> findByLocation(String location, Pageable pageable) {
        return wasteCollectionRepository.findByLocationContainingIgnoreCase(location, pageable);
    }

    public List<WasteCollection> findByStatus(String status) {
        return wasteCollectionRepository.findByStatus(status);
    }

    public Page<WasteCollection> findByStatus(String status, Pageable pageable) {
        return wasteCollectionRepository.findByStatus(status, pageable);
    }

    public List<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return wasteCollectionRepository.findByCollectionDateBetween(startDate, endDate);
    }

    public Page<WasteCollection> findByCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return wasteCollectionRepository.findByCollectionDateBetween(startDate, endDate, pageable);
    }

    public List<WasteCollection> searchWasteCollections(String wasteCategoryId, String location, String status,
                                                       LocalDateTime startDate, LocalDateTime endDate) {
        return wasteCollectionRepository.searchWasteCollections(wasteCategoryId, location, status, startDate, endDate);
    }

    // For backward compatibility with controllers still using Long
    public List<WasteCollection> searchWasteCollections(Long wasteCategoryId, String location, String status,
                                                       LocalDateTime startDate, LocalDateTime endDate) {
        return wasteCollectionRepository.searchWasteCollections(
            wasteCategoryId != null ? wasteCategoryId.toString() : null,
            location, status, startDate, endDate);
    }

    public Page<WasteCollection> searchWasteCollections(String wasteCategoryId, String location, String status,
                                                       LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return wasteCollectionRepository.searchWasteCollections(wasteCategoryId, location, status, startDate, endDate, pageable);
    }

    // For backward compatibility with controllers still using Long
    public Page<WasteCollection> searchWasteCollections(Long wasteCategoryId, String location, String status,
                                                       LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return wasteCollectionRepository.searchWasteCollections(
            wasteCategoryId != null ? wasteCategoryId.toString() : null,
            location, status, startDate, endDate, pageable);
    }
}
