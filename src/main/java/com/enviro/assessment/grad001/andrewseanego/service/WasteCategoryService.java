package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteCategoryService {
    private final WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    public WasteCategoryService(WasteCategoryRepository wasteCategoryRepository) {
        this.wasteCategoryRepository = wasteCategoryRepository;
    }

    public List<WasteCategory> getAllWasteCategories() {
        return wasteCategoryRepository.findAll();
    }

    public Page<WasteCategory> getAllWasteCategories(Pageable pageable) {
        return wasteCategoryRepository.findAll(pageable);
    }

    public Optional<WasteCategory> getWasteCategoryById(String id) {
        return wasteCategoryRepository.findById(id);
    }

    // For backward compatibility with controllers still using Long
    public Optional<WasteCategory> getWasteCategoryById(Long id) {
        return wasteCategoryRepository.findById(id.toString());
    }

    public WasteCategory saveWasteCategory(WasteCategory wasteCategory) {
        return wasteCategoryRepository.save(wasteCategory);
    }

    public void deleteWasteCategory(String id) {
        wasteCategoryRepository.deleteById(id);
    }

    // For backward compatibility with controllers still using Long
    public void deleteWasteCategory(Long id) {
        wasteCategoryRepository.deleteById(id.toString());
    }

    // Search methods
    public List<WasteCategory> findByName(String name) {
        return wasteCategoryRepository.findByNameContainingIgnoreCase(name);
    }

    public List<WasteCategory> findByDescription(String description) {
        return wasteCategoryRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<WasteCategory> findByRecyclable(boolean recyclable) {
        return wasteCategoryRepository.findByRecyclable(recyclable);
    }

    public List<WasteCategory> searchWasteCategories(String name, String description, Boolean recyclable) {
        return wasteCategoryRepository.searchWasteCategories(name, description, recyclable);
    }

    // Paginated search methods
    public Page<WasteCategory> findByName(String name, Pageable pageable) {
        return wasteCategoryRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<WasteCategory> findByDescription(String description, Pageable pageable) {
        return wasteCategoryRepository.findByDescriptionContainingIgnoreCase(description, pageable);
    }

    public Page<WasteCategory> findByRecyclable(boolean recyclable, Pageable pageable) {
        return wasteCategoryRepository.findByRecyclable(recyclable, pageable);
    }

    public Page<WasteCategory> searchWasteCategories(String name, String description, Boolean recyclable, Pageable pageable) {
        return wasteCategoryRepository.searchWasteCategories(name, description, recyclable, pageable);
    }
}