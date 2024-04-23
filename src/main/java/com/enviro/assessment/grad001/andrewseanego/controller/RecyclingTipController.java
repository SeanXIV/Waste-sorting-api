package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.RecyclingTipService;
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
@RequestMapping("/api/recycling-tips")
@Validated
public class RecyclingTipController {
    private final RecyclingTipService recyclingTipService;

    @Autowired
    public RecyclingTipController(RecyclingTipService recyclingTipService) {
        this.recyclingTipService = recyclingTipService;
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
