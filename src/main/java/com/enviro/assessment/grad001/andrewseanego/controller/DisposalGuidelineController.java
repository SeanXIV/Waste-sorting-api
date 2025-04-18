package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.DisposalGuidelineService;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/disposal-guidelines")
@Validated
public class DisposalGuidelineController {
    private final DisposalGuidelineService disposalGuidelineService;
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public DisposalGuidelineController(DisposalGuidelineService disposalGuidelineService, WasteCategoryService wasteCategoryService) {
        this.disposalGuidelineService = disposalGuidelineService;
        this.wasteCategoryService = wasteCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<DisposalGuideline>> getAllDisposalGuidelines() {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.getAllDisposalGuidelines();
        return ResponseEntity.ok(disposalGuidelines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDisposalGuidelineById(@PathVariable String id) {
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
    public ResponseEntity<DisposalGuideline> saveDisposalGuideline(@Valid @RequestBody DisposalGuideline disposalGuideline, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        DisposalGuideline savedDisposalGuideline = disposalGuidelineService.saveDisposalGuideline(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisposalGuideline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDisposalGuideline(@PathVariable String id) {
        try {
            disposalGuidelineService.deleteDisposalGuideline(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDisposalGuideline(@PathVariable String id, @Valid @RequestBody DisposalGuideline disposalGuideline, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            Optional<DisposalGuideline> optionalDisposalGuideline = disposalGuidelineService.getDisposalGuidelineById(id);
            if (!optionalDisposalGuideline.isPresent()) {
                throw new ResourceNotFoundException("Disposal Guideline not found with id: " + id);
            }

            disposalGuideline.setId(id); // Ensure the ID is set correctly
            DisposalGuideline updatedDisposalGuideline = disposalGuidelineService.saveDisposalGuideline(disposalGuideline);
            return ResponseEntity.ok(updatedDisposalGuideline);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateDisposalGuideline(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<DisposalGuideline> optionalDisposalGuideline = disposalGuidelineService.getDisposalGuidelineById(id);
            if (!optionalDisposalGuideline.isPresent()) {
                throw new ResourceNotFoundException("Disposal Guideline not found with id: " + id);
            }

            DisposalGuideline existingDisposalGuideline = optionalDisposalGuideline.get();

            // Apply updates to the existing disposal guideline
            if (updates.containsKey("description")) {
                existingDisposalGuideline.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("steps")) {
                existingDisposalGuideline.setSteps((String) updates.get("steps"));
            }
            if (updates.containsKey("legalRequirements")) {
                existingDisposalGuideline.setLegalRequirements((String) updates.get("legalRequirements"));
            }
            if (updates.containsKey("wasteCategory")) {
                // This would require more complex handling to convert from a map to a WasteCategory object
                // For simplicity, we'll skip this for now
            }

            DisposalGuideline updatedDisposalGuideline = disposalGuidelineService.saveDisposalGuideline(existingDisposalGuideline);
            return ResponseEntity.ok(updatedDisposalGuideline);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ClassCastException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data type in request");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DisposalGuideline>> searchDisposalGuidelines(
            @RequestParam(required = false) String wasteCategoryId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String steps,
            @RequestParam(required = false) String legalRequirements) {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.searchDisposalGuidelines(
                wasteCategoryId, description, steps, legalRequirements);
        return ResponseEntity.ok(disposalGuidelines);
    }

    @GetMapping("/search/waste-category/{wasteCategoryId}")
    public ResponseEntity<List<DisposalGuideline>> findByWasteCategory(@PathVariable String wasteCategoryId) {
        try {
            // First, get the waste category by ID
            WasteCategory wasteCategory = wasteCategoryService.getWasteCategoryById(wasteCategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + wasteCategoryId));

            List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.findByWasteCategory(wasteCategory);
            return ResponseEntity.ok(disposalGuidelines);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<DisposalGuideline>> findByDescription(@RequestParam String description) {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.findByDescription(description);
        return ResponseEntity.ok(disposalGuidelines);
    }

    @GetMapping("/search/steps")
    public ResponseEntity<List<DisposalGuideline>> findBySteps(@RequestParam String steps) {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.findBySteps(steps);
        return ResponseEntity.ok(disposalGuidelines);
    }

    @GetMapping("/search/legal-requirements")
    public ResponseEntity<List<DisposalGuideline>> findByLegalRequirements(@RequestParam String legalRequirements) {
        List<DisposalGuideline> disposalGuidelines = disposalGuidelineService.findByLegalRequirements(legalRequirements);
        return ResponseEntity.ok(disposalGuidelines);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
