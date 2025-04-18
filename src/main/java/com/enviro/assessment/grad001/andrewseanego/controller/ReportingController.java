package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.service.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reporting", description = "Waste Management Reporting APIs")
public class ReportingController {
    private final ReportingService reportingService;

    @Autowired
    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Operation(summary = "Get waste collections by category", description = "Returns a report of waste collections grouped by category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/by-category")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getWasteCollectionsByCategory() {
        Map<String, Double> report = reportingService.getWasteCollectionsByCategory();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Get waste collections by location", description = "Returns a report of waste collections grouped by location")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/by-location")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getWasteCollectionsByLocation() {
        Map<String, Double> report = reportingService.getWasteCollectionsByLocation();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Get waste collections by date range", description = "Returns a report of waste collections within a date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/by-date-range")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getWasteCollectionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Map<String, Double> report = reportingService.getWasteCollectionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Get recyclable vs non-recyclable waste", description = "Returns a report comparing recyclable and non-recyclable waste")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/recyclable-vs-non-recyclable")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getRecyclableVsNonRecyclableWaste() {
        Map<String, Double> report = reportingService.getRecyclableVsNonRecyclableWaste();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Get waste collections by status", description = "Returns a report of waste collections grouped by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/by-status")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getWasteCollectionsByStatus() {
        Map<String, Double> report = reportingService.getWasteCollectionsByStatus();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Get comprehensive report", description = "Returns a comprehensive report with all metrics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated report",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/comprehensive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getComprehensiveReport() {
        Map<String, Object> report = reportingService.getComprehensiveReport();
        return ResponseEntity.ok(report);
    }
}
