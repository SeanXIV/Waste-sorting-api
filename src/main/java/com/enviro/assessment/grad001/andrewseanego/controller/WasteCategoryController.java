package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/waste-categories")
@Validated
@Tag(name = "Waste Category", description = "Waste Category management APIs")
public class WasteCategoryController {
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public WasteCategoryController(WasteCategoryService wasteCategoryService) {
        this.wasteCategoryService = wasteCategoryService;
    }

    @Operation(summary = "Get all waste categories", description = "Returns a list of all waste categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = WasteCategory.class)))
    })
    @GetMapping
    public ResponseEntity<List<WasteCategory>> getAllWasteCategories() {
        List<WasteCategory> wasteCategories = wasteCategoryService.getAllWasteCategories();
        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WasteCategory>> getAllWasteCategoriesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WasteCategory> wasteCategories = wasteCategoryService.getAllWasteCategories(pageable);

        return ResponseEntity.ok(wasteCategories);
    }

    @Operation(summary = "Get a waste category by ID", description = "Returns a waste category as per the ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved waste category",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = WasteCategory.class))),
        @ApiResponse(responseCode = "404", description = "Waste category not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getWasteCategoryById(
            @Parameter(description = "ID of waste category to be retrieved") @PathVariable Long id) {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWasteCategory(@PathVariable Long id, @Valid @RequestBody WasteCategory wasteCategory, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            Optional<WasteCategory> optionalWasteCategory = wasteCategoryService.getWasteCategoryById(id);
            if (!optionalWasteCategory.isPresent()) {
                throw new ResourceNotFoundException("Waste Category not found with id: " + id);
            }

            wasteCategory.setId(id); // Ensure the ID is set correctly
            WasteCategory updatedWasteCategory = wasteCategoryService.saveWasteCategory(wasteCategory);
            return ResponseEntity.ok(updatedWasteCategory);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateWasteCategory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<WasteCategory> optionalWasteCategory = wasteCategoryService.getWasteCategoryById(id);
            if (!optionalWasteCategory.isPresent()) {
                throw new ResourceNotFoundException("Waste Category not found with id: " + id);
            }

            WasteCategory existingWasteCategory = optionalWasteCategory.get();

            // Apply updates to the existing waste category
            if (updates.containsKey("name")) {
                existingWasteCategory.setName((String) updates.get("name"));
            }
            if (updates.containsKey("description")) {
                existingWasteCategory.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("recyclable")) {
                existingWasteCategory.setRecyclable((Boolean) updates.get("recyclable"));
            }
            if (updates.containsKey("disposalMethods")) {
                existingWasteCategory.setDisposalMethods((List<String>) updates.get("disposalMethods"));
            }

            WasteCategory updatedWasteCategory = wasteCategoryService.saveWasteCategory(existingWasteCategory);
            return ResponseEntity.ok(updatedWasteCategory);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ClassCastException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data type in request");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<WasteCategory>> searchWasteCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean recyclable,
            @RequestParam(required = false) String disposalMethod) {
        List<WasteCategory> wasteCategories = wasteCategoryService.searchWasteCategories(name, description, recyclable);

        // Additional filtering for disposal methods
        if (disposalMethod != null && !disposalMethod.isEmpty()) {
            wasteCategories = wasteCategories.stream()
                .filter(category -> category.getDisposalMethods() != null &&
                        category.getDisposalMethods().stream()
                            .anyMatch(method -> method.toLowerCase().contains(disposalMethod.toLowerCase())))
                .collect(Collectors.toList());
        }

        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/search/paged")
    public ResponseEntity<Page<WasteCategory>> searchWasteCategoriesPaged(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean recyclable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WasteCategory> wasteCategories = wasteCategoryService.searchWasteCategories(name, description, recyclable, pageable);

        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<WasteCategory>> findByName(@RequestParam String name) {
        List<WasteCategory> wasteCategories = wasteCategoryService.findByName(name);
        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<WasteCategory>> findByDescription(@RequestParam String description) {
        List<WasteCategory> wasteCategories = wasteCategoryService.findByDescription(description);
        return ResponseEntity.ok(wasteCategories);
    }

    @GetMapping("/search/recyclable")
    public ResponseEntity<List<WasteCategory>> findByRecyclable(@RequestParam boolean recyclable) {
        List<WasteCategory> wasteCategories = wasteCategoryService.findByRecyclable(recyclable);
        return ResponseEntity.ok(wasteCategories);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
