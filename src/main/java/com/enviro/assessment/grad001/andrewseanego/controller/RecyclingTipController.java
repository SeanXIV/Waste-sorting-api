package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.RecyclingTipService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recycling-tips")
@Validated
public class RecyclingTipController {
    private final RecyclingTipService recyclingTipService;
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public RecyclingTipController(RecyclingTipService recyclingTipService, WasteCategoryService wasteCategoryService) {
        this.recyclingTipService = recyclingTipService;
        this.wasteCategoryService = wasteCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<RecyclingTip>> getAllRecyclingTips() {
        List<RecyclingTip> recyclingTips = recyclingTipService.getAllRecyclingTips();
        return ResponseEntity.ok(recyclingTips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecyclingTipById(@PathVariable Long id) {
        try {
            Optional<RecyclingTip> optionalRecyclingTip = recyclingTipService.getRecyclingTipById(id);
            RecyclingTip recyclingTip = optionalRecyclingTip.orElseThrow(() -> new ResourceNotFoundException("Recycling Tip not found with id: " + id));
            return ResponseEntity.ok(recyclingTip);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping
    public ResponseEntity<RecyclingTip> saveRecyclingTip(@Valid @RequestBody RecyclingTip recyclingTip, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        RecyclingTip savedRecyclingTip = recyclingTipService.saveRecyclingTip(recyclingTip);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecyclingTip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecyclingTip(@PathVariable Long id) {
        try {
            recyclingTipService.deleteRecyclingTip(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecyclingTip(@PathVariable Long id, @Valid @RequestBody RecyclingTip recyclingTip, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            Optional<RecyclingTip> optionalRecyclingTip = recyclingTipService.getRecyclingTipById(id);
            if (!optionalRecyclingTip.isPresent()) {
                throw new ResourceNotFoundException("Recycling Tip not found with id: " + id);
            }

            recyclingTip.setId(id); // Ensure the ID is set correctly
            RecyclingTip updatedRecyclingTip = recyclingTipService.saveRecyclingTip(recyclingTip);
            return ResponseEntity.ok(updatedRecyclingTip);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateRecyclingTip(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<RecyclingTip> optionalRecyclingTip = recyclingTipService.getRecyclingTipById(id);
            if (!optionalRecyclingTip.isPresent()) {
                throw new ResourceNotFoundException("Recycling Tip not found with id: " + id);
            }

            RecyclingTip existingRecyclingTip = optionalRecyclingTip.get();

            // Apply updates to the existing recycling tip
            if (updates.containsKey("title")) {
                existingRecyclingTip.setTitle((String) updates.get("title"));
            }
            if (updates.containsKey("description")) {
                existingRecyclingTip.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("source")) {
                existingRecyclingTip.setSource((String) updates.get("source"));
            }
            if (updates.containsKey("wasteCategory")) {
                // This would require more complex handling to convert from a map to a WasteCategory object
                // For simplicity, we'll skip this for now
            }

            RecyclingTip updatedRecyclingTip = recyclingTipService.saveRecyclingTip(existingRecyclingTip);
            return ResponseEntity.ok(updatedRecyclingTip);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ClassCastException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data type in request");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecyclingTip>> searchRecyclingTips(
            @RequestParam(required = false) Long wasteCategoryId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) Boolean recyclableOnly) {
        List<RecyclingTip> recyclingTips = recyclingTipService.searchRecyclingTips(
                wasteCategoryId, title, description, source);

        // Additional filtering for recyclable waste categories only
        if (recyclableOnly != null && recyclableOnly) {
            recyclingTips = recyclingTips.stream()
                .filter(tip -> tip.getWasteCategory() != null && tip.getWasteCategory().isRecyclable())
                .collect(Collectors.toList());
        }

        return ResponseEntity.ok(recyclingTips);
    }

    @GetMapping("/search/waste-category/{wasteCategoryId}")
    public ResponseEntity<List<RecyclingTip>> findByWasteCategory(@PathVariable Long wasteCategoryId) {
        try {
            // First, get the waste category by ID
            WasteCategory wasteCategory = wasteCategoryService.getWasteCategoryById(wasteCategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + wasteCategoryId));

            List<RecyclingTip> recyclingTips = recyclingTipService.findByWasteCategory(wasteCategory);
            return ResponseEntity.ok(recyclingTips);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<RecyclingTip>> findByTitle(@RequestParam String title) {
        List<RecyclingTip> recyclingTips = recyclingTipService.findByTitle(title);
        return ResponseEntity.ok(recyclingTips);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<RecyclingTip>> findByDescription(@RequestParam String description) {
        List<RecyclingTip> recyclingTips = recyclingTipService.findByDescription(description);
        return ResponseEntity.ok(recyclingTips);
    }

    @GetMapping("/search/source")
    public ResponseEntity<List<RecyclingTip>> findBySource(@RequestParam String source) {
        List<RecyclingTip> recyclingTips = recyclingTipService.findBySource(source);
        return ResponseEntity.ok(recyclingTips);
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
