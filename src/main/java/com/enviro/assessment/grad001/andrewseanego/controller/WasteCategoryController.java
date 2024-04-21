package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/waste-categories")
public class WasteCategoryController {
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public WasteCategoryController(WasteCategoryService wasteCategoryService) {
        this.wasteCategoryService = wasteCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<WasteCategory>> getAllWasteCategories() {
        List<WasteCategory> wasteCategories = wasteCategoryService.getAllWasteCategories();
        return new ResponseEntity<>(wasteCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WasteCategory> getWasteCategoryById(@PathVariable("id") Long id) {
        Optional<WasteCategory> wasteCategory = wasteCategoryService.getWasteCategoryById(id);
        return wasteCategory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<WasteCategory> saveWasteCategory(@RequestBody WasteCategory wasteCategory) {
        WasteCategory savedWasteCategory = wasteCategoryService.saveWasteCategory(wasteCategory);
        return new ResponseEntity<>(savedWasteCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable("id") Long id) {
        wasteCategoryService.deleteWasteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
