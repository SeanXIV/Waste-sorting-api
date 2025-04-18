package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.DisposalGuidelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposalGuidelineService {
    private final DisposalGuidelineRepository disposalGuidelineRepository;

    @Autowired
    public DisposalGuidelineService(DisposalGuidelineRepository disposalGuidelineRepository) {
        this.disposalGuidelineRepository = disposalGuidelineRepository;
    }

    public List<DisposalGuideline> getAllDisposalGuidelines() {
        return disposalGuidelineRepository.findAll();
    }

    public Optional<DisposalGuideline> getDisposalGuidelineById(Long id) {
        return disposalGuidelineRepository.findById(id);
    }

    public DisposalGuideline saveDisposalGuideline(DisposalGuideline disposalGuideline) {
        return disposalGuidelineRepository.save(disposalGuideline);
    }

    public void deleteDisposalGuideline(Long id) {
        disposalGuidelineRepository.deleteById(id);
    }

    // Search methods
    public List<DisposalGuideline> findByWasteCategory(WasteCategory wasteCategory) {
        return disposalGuidelineRepository.findByWasteCategory(wasteCategory);
    }

    public List<DisposalGuideline> findByDescription(String description) {
        return disposalGuidelineRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<DisposalGuideline> findBySteps(String steps) {
        return disposalGuidelineRepository.findByStepsContainingIgnoreCase(steps);
    }

    public List<DisposalGuideline> findByLegalRequirements(String legalRequirements) {
        return disposalGuidelineRepository.findByLegalRequirementsContainingIgnoreCase(legalRequirements);
    }

    public List<DisposalGuideline> searchDisposalGuidelines(Long wasteCategoryId, String description, String steps, String legalRequirements) {
        return disposalGuidelineRepository.searchDisposalGuidelines(wasteCategoryId, description, steps, legalRequirements);
    }
}