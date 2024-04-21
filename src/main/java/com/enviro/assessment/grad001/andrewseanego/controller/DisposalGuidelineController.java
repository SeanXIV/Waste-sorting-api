package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.service.DisposalGuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disposal-guidelines")
public class DisposalGuidelineController {
    private final DisposalGuidelineService disposalGuidelineService;

    @Autowired
    public DisposalGuidelineController(DisposalGuidelineService disposalGuidelineService) {
        this.disposalGuidelineService = disposalGuidelineService;
    }

    @GetMapping
    public ResponseEntity<List<DisposalGuideline>> getAllDisposalGuidelines() {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.getAllDisposalGuidelines();
        return ResponseEntity.ok(disposalGuidelines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDisposalGuidelineById(@PathVariable Long id) {
        try {
            Optional<DisposalGuideline> optionalDisposalGuideline = disposalGuidelineService.getDisposalGuidelineById(id);
            DisposalGuideline disposalGuideline = optionalDisposalGuideline.orElseThrow(() -> new ResourceNotFoundException("Disposal Guideline not found with id: " + id));
            return ResponseEntity.ok(disposalGuideline);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping
    public ResponseEntity<DisposalGuideline> saveDisposalGuideline(@RequestBody DisposalGuideline disposalGuideline) {
        DisposalGuideline savedDisposalGuideline = disposalGuidelineService.saveDisposalGuideline(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisposalGuideline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDisposalGuideline(@PathVariable Long id) {
        try {
            disposalGuidelineService.deleteDisposalGuideline(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
