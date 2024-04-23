package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/waste-categories")
@Validated
public class WasteCategoryController {
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public WasteCategoryController(WasteCategoryService wasteCategoryService) {
        this.wasteCategoryService = wasteCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<WasteCategory>> getAllWasteCategories() {
        List<WasteCategory> wasteCategories = wasteCategoryService.getAllWasteCategories();
        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWasteCategoryById(@PathVariable Long id) {
        try {
            Optional<WasteCategory> optionalWasteCategory = wasteCategoryService.getWasteCategoryById(id);
            WasteCategory wasteCategory = optionalWasteCategory.orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + id));
            return ResponseEntity.ok(wasteCategory);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping
    public ResponseEntity<WasteCategory> saveWasteCategory(@Valid @RequestBody WasteCategory wasteCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        WasteCategory savedWasteCategory = wasteCategoryService.saveWasteCategory(wasteCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWasteCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWasteCategory(@PathVariable Long id) {
        try {
            wasteCategoryService.deleteWasteCategory(id);
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
