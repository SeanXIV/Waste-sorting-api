package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingCenter;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.RecyclingCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingCenterService {
    private final RecyclingCenterRepository recyclingCenterRepository;

    @Autowired
    public RecyclingCenterService(RecyclingCenterRepository recyclingCenterRepository) {
        this.recyclingCenterRepository = recyclingCenterRepository;
    }

    public List<RecyclingCenter> getAllRecyclingCenters() {
        return recyclingCenterRepository.findAll();
    }

    public Page<RecyclingCenter> getAllRecyclingCenters(Pageable pageable) {
        return recyclingCenterRepository.findAll(pageable);
    }

    public Optional<RecyclingCenter> getRecyclingCenterById(Long id) {
        return recyclingCenterRepository.findById(id);
    }

    public RecyclingCenter saveRecyclingCenter(RecyclingCenter recyclingCenter) {
        return recyclingCenterRepository.save(recyclingCenter);
    }

    public void deleteRecyclingCenter(Long id) {
        recyclingCenterRepository.deleteById(id);
    }

    // Search methods
    public List<RecyclingCenter> findByName(String name) {
        return recyclingCenterRepository.findByNameContainingIgnoreCase(name);
    }

    public Page<RecyclingCenter> findByName(String name, Pageable pageable) {
        return recyclingCenterRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public List<RecyclingCenter> findByAddress(String address) {
        return recyclingCenterRepository.findByAddressContainingIgnoreCase(address);
    }

    public Page<RecyclingCenter> findByAddress(String address, Pageable pageable) {
        return recyclingCenterRepository.findByAddressContainingIgnoreCase(address, pageable);
    }

    public List<RecyclingCenter> findByActive(boolean active) {
        return recyclingCenterRepository.findByActive(active);
    }

    public Page<RecyclingCenter> findByActive(boolean active, Pageable pageable) {
        return recyclingCenterRepository.findByActive(active, pageable);
    }

    public List<RecyclingCenter> findByAcceptedWasteCategory(WasteCategory wasteCategory) {
        return recyclingCenterRepository.findByAcceptedWasteCategory(wasteCategory);
    }

    public Page<RecyclingCenter> findByAcceptedWasteCategory(WasteCategory wasteCategory, Pageable pageable) {
        return recyclingCenterRepository.findByAcceptedWasteCategory(wasteCategory, pageable);
    }

    public List<RecyclingCenter> searchRecyclingCenters(String name, String address, Boolean active) {
        return recyclingCenterRepository.searchRecyclingCenters(name, address, active);
    }

    public Page<RecyclingCenter> searchRecyclingCenters(String name, String address, Boolean active, Pageable pageable) {
        return recyclingCenterRepository.searchRecyclingCenters(name, address, active, pageable);
    }
}
