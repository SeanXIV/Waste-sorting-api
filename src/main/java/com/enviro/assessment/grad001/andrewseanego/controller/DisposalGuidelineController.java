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
        return new ResponseEntity<>(disposalGuidelines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuideline> getDisposalGuidelineById(@PathVariable("id") Long id) {
        Optional<DisposalGuideline> disposalGuideline = disposalGuidelineService.getDisposalGuidelineById(id);
        return disposalGuideline.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<DisposalGuideline> saveDisposalGuideline(@RequestBody DisposalGuideline disposalGuideline) {
        DisposalGuideline savedDisposalGuideline = disposalGuidelineService.saveDisposalGuideline(disposalGuideline);
        return new ResponseEntity<>(savedDisposalGuideline, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisposalGuideline(@PathVariable("id") Long id) {
        disposalGuidelineService.deleteDisposalGuideline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
