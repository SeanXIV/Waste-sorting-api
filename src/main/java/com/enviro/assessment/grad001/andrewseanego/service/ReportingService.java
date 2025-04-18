package com.enviro.assessment.grad001.andrewseanego.service;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCollection;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    private final WasteCollectionRepository wasteCollectionRepository;
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public ReportingService(WasteCollectionRepository wasteCollectionRepository, WasteCategoryService wasteCategoryService) {
        this.wasteCollectionRepository = wasteCollectionRepository;
        this.wasteCategoryService = wasteCategoryService;
    }

    /**
     * Generate a report of waste collections by category
     * @return Map of waste category name to total quantity collected
     */
    public Map<String, Double> getWasteCollectionsByCategory() {
        List<WasteCollection> allCollections = wasteCollectionRepository.findAll();
        Map<String, Double> report = new HashMap<>();

        // Group collections by waste category and sum quantities
        for (WasteCollection collection : allCollections) {
            String categoryName = collection.getWasteCategory().getName();
            Double quantity = collection.getQuantity();
            
            report.put(categoryName, report.getOrDefault(categoryName, 0.0) + quantity);
        }

        return report;
    }

    /**
     * Generate a report of waste collections by location
     * @return Map of location to total quantity collected
     */
    public Map<String, Double> getWasteCollectionsByLocation() {
        List<WasteCollection> allCollections = wasteCollectionRepository.findAll();
        Map<String, Double> report = new HashMap<>();

        // Group collections by location and sum quantities
        for (WasteCollection collection : allCollections) {
            String location = collection.getLocation();
            Double quantity = collection.getQuantity();
            
            report.put(location, report.getOrDefault(location, 0.0) + quantity);
        }

        return report;
    }

    /**
     * Generate a report of waste collections by date range
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return Map of date to total quantity collected
     */
    public Map<String, Double> getWasteCollectionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<WasteCollection> collections = wasteCollectionRepository.findByCollectionDateBetween(startDate, endDate);
        Map<String, Double> report = new HashMap<>();

        // Group collections by date and sum quantities
        for (WasteCollection collection : collections) {
            String date = collection.getCollectionDate().toLocalDate().toString();
            Double quantity = collection.getQuantity();
            
            report.put(date, report.getOrDefault(date, 0.0) + quantity);
        }

        return report;
    }

    /**
     * Generate a report of recyclable vs non-recyclable waste
     * @return Map with "Recyclable" and "Non-Recyclable" keys and their total quantities
     */
    public Map<String, Double> getRecyclableVsNonRecyclableWaste() {
        List<WasteCollection> allCollections = wasteCollectionRepository.findAll();
        Map<String, Double> report = new HashMap<>();
        report.put("Recyclable", 0.0);
        report.put("Non-Recyclable", 0.0);

        // Group collections by recyclable status and sum quantities
        for (WasteCollection collection : allCollections) {
            boolean recyclable = collection.getWasteCategory().isRecyclable();
            Double quantity = collection.getQuantity();
            
            if (recyclable) {
                report.put("Recyclable", report.get("Recyclable") + quantity);
            } else {
                report.put("Non-Recyclable", report.get("Non-Recyclable") + quantity);
            }
        }

        return report;
    }

    /**
     * Generate a report of waste collections by status
     * @return Map of status to total quantity collected
     */
    public Map<String, Double> getWasteCollectionsByStatus() {
        List<WasteCollection> allCollections = wasteCollectionRepository.findAll();
        Map<String, Double> report = new HashMap<>();

        // Group collections by status and sum quantities
        for (WasteCollection collection : allCollections) {
            String status = collection.getStatus();
            Double quantity = collection.getQuantity();
            
            report.put(status, report.getOrDefault(status, 0.0) + quantity);
        }

        return report;
    }

    /**
     * Generate a comprehensive report with all metrics
     * @return Map containing various report metrics
     */
    public Map<String, Object> getComprehensiveReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Add all report types to the comprehensive report
        report.put("byCategory", getWasteCollectionsByCategory());
        report.put("byLocation", getWasteCollectionsByLocation());
        report.put("byStatus", getWasteCollectionsByStatus());
        report.put("recyclableVsNonRecyclable", getRecyclableVsNonRecyclableWaste());
        
        // Add total waste collected
        List<WasteCollection> allCollections = wasteCollectionRepository.findAll();
        double totalWaste = allCollections.stream().mapToDouble(WasteCollection::getQuantity).sum();
        report.put("totalWasteCollected", totalWaste);
        
        // Add count of collections
        report.put("totalCollections", allCollections.size());
        
        return report;
    }
}
