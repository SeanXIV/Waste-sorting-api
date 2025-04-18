package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCollection;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/waste-collections")
@Validated
@Tag(name = "Waste Collection", description = "Waste Collection management APIs")
public class WasteCollectionController {
    private final WasteCollectionService wasteCollectionService;
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public WasteCollectionController(WasteCollectionService wasteCollectionService, WasteCategoryService wasteCategoryService) {
        this.wasteCollectionService = wasteCollectionService;
        this.wasteCategoryService = wasteCategoryService;
    }

    @Operation(summary = "Get all waste collections", description = "Returns a list of all waste collections")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = WasteCollection.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> getAllWasteCollections() {
        List<WasteCollection> wasteCollections = wasteCollectionService.getAllWasteCollections();
        return ResponseEntity.ok(wasteCollections);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<WasteCollection>> getAllWasteCollectionsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WasteCollection> wasteCollections = wasteCollectionService.getAllWasteCollections(pageable);

        return ResponseEntity.ok(wasteCollections);
    }

    @Operation(summary = "Get a waste collection by ID", description = "Returns a waste collection as per the ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved waste collection",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = WasteCollection.class))),
        @ApiResponse(responseCode = "404", description = "Waste collection not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getWasteCollectionById(
            @Parameter(description = "ID of waste collection to be retrieved") @PathVariable Long id) {
        try {
            Optional<WasteCollection> optionalWasteCollection = wasteCollectionService.getWasteCollectionById(id);
            WasteCollection wasteCollection = optionalWasteCollection.orElseThrow(() -> new ResourceNotFoundException("Waste Collection not found with id: " + id));
            return ResponseEntity.ok(wasteCollection);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<WasteCollection> saveWasteCollection(@Valid @RequestBody WasteCollection wasteCollection, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        WasteCollection savedWasteCollection = wasteCollectionService.saveWasteCollection(wasteCollection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWasteCollection);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateWasteCollection(@PathVariable Long id, @Valid @RequestBody WasteCollection wasteCollection, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            Optional<WasteCollection> optionalWasteCollection = wasteCollectionService.getWasteCollectionById(id);
            if (!optionalWasteCollection.isPresent()) {
                throw new ResourceNotFoundException("Waste Collection not found with id: " + id);
            }

            wasteCollection.setId(id.toString()); // Ensure the ID is set correctly
            WasteCollection updatedWasteCollection = wasteCollectionService.saveWasteCollection(wasteCollection);
            return ResponseEntity.ok(updatedWasteCollection);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> partialUpdateWasteCollection(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<WasteCollection> optionalWasteCollection = wasteCollectionService.getWasteCollectionById(id);
            if (!optionalWasteCollection.isPresent()) {
                throw new ResourceNotFoundException("Waste Collection not found with id: " + id);
            }

            WasteCollection existingWasteCollection = optionalWasteCollection.get();

            // Apply updates to the existing waste collection
            if (updates.containsKey("location")) {
                existingWasteCollection.setLocation((String) updates.get("location"));
            }
            if (updates.containsKey("status")) {
                existingWasteCollection.setStatus((String) updates.get("status"));
            }
            if (updates.containsKey("notes")) {
                existingWasteCollection.setNotes((String) updates.get("notes"));
            }
            if (updates.containsKey("quantity")) {
                existingWasteCollection.setQuantity((Double) updates.get("quantity"));
            }
            if (updates.containsKey("unit")) {
                existingWasteCollection.setUnit((String) updates.get("unit"));
            }
            if (updates.containsKey("collectionDate")) {
                existingWasteCollection.setCollectionDate(LocalDateTime.parse((String) updates.get("collectionDate")));
            }
            if (updates.containsKey("wasteCategoryId")) {
                Long wasteCategoryId = Long.valueOf(updates.get("wasteCategoryId").toString());
                WasteCategory wasteCategory = wasteCategoryService.getWasteCategoryById(wasteCategoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + wasteCategoryId));
                existingWasteCollection.setWasteCategory(wasteCategory);
            }

            WasteCollection updatedWasteCollection = wasteCollectionService.saveWasteCollection(existingWasteCollection);
            return ResponseEntity.ok(updatedWasteCollection);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ClassCastException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data type in request");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteWasteCollection(@PathVariable Long id) {
        try {
            wasteCollectionService.deleteWasteCollection(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> searchWasteCollections(
            @RequestParam(required = false) Long wasteCategoryId,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<WasteCollection> wasteCollections = wasteCollectionService.searchWasteCollections(
                wasteCategoryId, location, status, startDate, endDate);
        return ResponseEntity.ok(wasteCollections);
    }

    @GetMapping("/search/paged")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<WasteCollection>> searchWasteCollectionsPaged(
            @RequestParam(required = false) Long wasteCategoryId,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<WasteCollection> wasteCollections = wasteCollectionService.searchWasteCollections(
                wasteCategoryId, location, status, startDate, endDate, pageable);

        return ResponseEntity.ok(wasteCollections);
    }

    @GetMapping("/search/waste-category/{wasteCategoryId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> findByWasteCategory(@PathVariable Long wasteCategoryId) {
        try {
            // First, get the waste category by ID
            WasteCategory wasteCategory = wasteCategoryService.getWasteCategoryById(wasteCategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + wasteCategoryId));

            List<WasteCollection> wasteCollections = wasteCollectionService.findByWasteCategory(wasteCategory);
            return ResponseEntity.ok(wasteCollections);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/search/location")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> findByLocation(@RequestParam String location) {
        List<WasteCollection> wasteCollections = wasteCollectionService.findByLocation(location);
        return ResponseEntity.ok(wasteCollections);
    }

    @GetMapping("/search/status")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> findByStatus(@RequestParam String status) {
        List<WasteCollection> wasteCollections = wasteCollectionService.findByStatus(status);
        return ResponseEntity.ok(wasteCollections);
    }

    @GetMapping("/search/date-range")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<WasteCollection>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<WasteCollection> wasteCollections = wasteCollectionService.findByCollectionDateBetween(startDate, endDate);
        return ResponseEntity.ok(wasteCollections);
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
