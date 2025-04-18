package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.RecyclingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingTipService {
    private final RecyclingTipRepository recyclingTipRepository;

    @Autowired
    public RecyclingTipService(RecyclingTipRepository recyclingTipRepository) {
        this.recyclingTipRepository = recyclingTipRepository;
    }

    public List<RecyclingTip> getAllRecyclingTips() {
        return recyclingTipRepository.findAll();
    }

    public Optional<RecyclingTip> getRecyclingTipById(String id) {
        return recyclingTipRepository.findById(id);
    }

    // For backward compatibility with controllers still using Long
    public Optional<RecyclingTip> getRecyclingTipById(Long id) {
        return id == null ? Optional.empty() : recyclingTipRepository.findById(id.toString());
    }

    public RecyclingTip saveRecyclingTip(RecyclingTip recyclingTip) {
        return recyclingTipRepository.save(recyclingTip);
    }

    public void deleteRecyclingTip(String id) {
        recyclingTipRepository.deleteById(id);
    }

    // For backward compatibility with controllers still using Long
    public void deleteRecyclingTip(Long id) {
        if (id != null) {
            recyclingTipRepository.deleteById(id.toString());
        }
    }

    // Search methods
    public List<RecyclingTip> findByWasteCategory(WasteCategory wasteCategory) {
        return recyclingTipRepository.findByWasteCategory(wasteCategory);
    }

    public List<RecyclingTip> findByTitle(String title) {
        return recyclingTipRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<RecyclingTip> findByDescription(String description) {
        return recyclingTipRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<RecyclingTip> findBySource(String source) {
        return recyclingTipRepository.findBySourceContainingIgnoreCase(source);
    }

    public List<RecyclingTip> searchRecyclingTips(String wasteCategoryId, String title, String description, String source) {
        return recyclingTipRepository.searchRecyclingTips(wasteCategoryId, title, description, source);
    }

    // For backward compatibility with controllers still using Long
    public List<RecyclingTip> searchRecyclingTips(Long wasteCategoryId, String title, String description, String source) {
        return searchRecyclingTips(
            wasteCategoryId != null ? wasteCategoryId.toString() : null,
            title,
            description,
            source
        );
    }
}
