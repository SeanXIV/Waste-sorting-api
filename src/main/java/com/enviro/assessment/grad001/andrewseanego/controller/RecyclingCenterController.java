package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingCenter;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.RecyclingCenterService;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recycling-centers")
@Validated
@Tag(name = "Recycling Center", description = "Recycling Center management APIs")
public class RecyclingCenterController {
    private final RecyclingCenterService recyclingCenterService;
    private final WasteCategoryService wasteCategoryService;

    @Autowired
    public RecyclingCenterController(RecyclingCenterService recyclingCenterService, WasteCategoryService wasteCategoryService) {
        this.recyclingCenterService = recyclingCenterService;
        this.wasteCategoryService = wasteCategoryService;
    }

    @Operation(summary = "Get all recycling centers", description = "Returns a list of all recycling centers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RecyclingCenter.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> getAllRecyclingCenters() {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.getAllRecyclingCenters();
        return ResponseEntity.ok(recyclingCenters);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RecyclingCenter>> getAllRecyclingCentersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RecyclingCenter> recyclingCenters = recyclingCenterService.getAllRecyclingCenters(pageable);
        
        return ResponseEntity.ok(recyclingCenters);
    }

    @Operation(summary = "Get a recycling center by ID", description = "Returns a recycling center as per the ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved recycling center",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = RecyclingCenter.class))),
        @ApiResponse(responseCode = "404", description = "Recycling center not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getRecyclingCenterById(
            @Parameter(description = "ID of recycling center to be retrieved") @PathVariable Long id) {
        try {
            Optional<RecyclingCenter> optionalRecyclingCenter = recyclingCenterService.getRecyclingCenterById(id);
            RecyclingCenter recyclingCenter = optionalRecyclingCenter.orElseThrow(() -> new ResourceNotFoundException("Recycling Center not found with id: " + id));
            return ResponseEntity.ok(recyclingCenter);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RecyclingCenter> saveRecyclingCenter(@Valid @RequestBody RecyclingCenter recyclingCenter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        RecyclingCenter savedRecyclingCenter = recyclingCenterService.saveRecyclingCenter(recyclingCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecyclingCenter);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateRecyclingCenter(@PathVariable Long id, @Valid @RequestBody RecyclingCenter recyclingCenter, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            
            Optional<RecyclingCenter> optionalRecyclingCenter = recyclingCenterService.getRecyclingCenterById(id);
            if (!optionalRecyclingCenter.isPresent()) {
                throw new ResourceNotFoundException("Recycling Center not found with id: " + id);
            }
            
            recyclingCenter.setId(id); // Ensure the ID is set correctly
            RecyclingCenter updatedRecyclingCenter = recyclingCenterService.saveRecyclingCenter(recyclingCenter);
            return ResponseEntity.ok(updatedRecyclingCenter);
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
    public ResponseEntity<?> partialUpdateRecyclingCenter(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<RecyclingCenter> optionalRecyclingCenter = recyclingCenterService.getRecyclingCenterById(id);
            if (!optionalRecyclingCenter.isPresent()) {
                throw new ResourceNotFoundException("Recycling Center not found with id: " + id);
            }
            
            RecyclingCenter existingRecyclingCenter = optionalRecyclingCenter.get();
            
            // Apply updates to the existing recycling center
            if (updates.containsKey("name")) {
                existingRecyclingCenter.setName((String) updates.get("name"));
            }
            if (updates.containsKey("address")) {
                existingRecyclingCenter.setAddress((String) updates.get("address"));
            }
            if (updates.containsKey("contactInfo")) {
                existingRecyclingCenter.setContactInfo((String) updates.get("contactInfo"));
            }
            if (updates.containsKey("description")) {
                existingRecyclingCenter.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("operatingHours")) {
                existingRecyclingCenter.setOperatingHours((String) updates.get("operatingHours"));
            }
            if (updates.containsKey("active")) {
                existingRecyclingCenter.setActive((Boolean) updates.get("active"));
            }
            
            RecyclingCenter updatedRecyclingCenter = recyclingCenterService.saveRecyclingCenter(existingRecyclingCenter);
            return ResponseEntity.ok(updatedRecyclingCenter);
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
    public ResponseEntity<String> deleteRecyclingCenter(@PathVariable Long id) {
        try {
            recyclingCenterService.deleteRecyclingCenter(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> searchRecyclingCenters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Boolean active) {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.searchRecyclingCenters(name, address, active);
        return ResponseEntity.ok(recyclingCenters);
    }

    @GetMapping("/search/paged")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<RecyclingCenter>> searchRecyclingCentersPaged(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RecyclingCenter> recyclingCenters = recyclingCenterService.searchRecyclingCenters(name, address, active, pageable);
        
        return ResponseEntity.ok(recyclingCenters);
    }

    @GetMapping("/search/waste-category/{wasteCategoryId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> findByAcceptedWasteCategory(@PathVariable Long wasteCategoryId) {
        try {
            // First, get the waste category by ID
            WasteCategory wasteCategory = wasteCategoryService.getWasteCategoryById(wasteCategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Waste Category not found with id: " + wasteCategoryId));
            
            List<RecyclingCenter> recyclingCenters = recyclingCenterService.findByAcceptedWasteCategory(wasteCategory);
            return ResponseEntity.ok(recyclingCenters);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/search/name")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> findByName(@RequestParam String name) {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.findByName(name);
        return ResponseEntity.ok(recyclingCenters);
    }

    @GetMapping("/search/address")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> findByAddress(@RequestParam String address) {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.findByAddress(address);
        return ResponseEntity.ok(recyclingCenters);
    }

    @GetMapping("/search/active")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecyclingCenter>> findByActive(@RequestParam boolean active) {
        List<RecyclingCenter> recyclingCenters = recyclingCenterService.findByActive(active);
        return ResponseEntity.ok(recyclingCenters);
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
