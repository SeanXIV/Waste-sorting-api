package com.enviro.assessment.grad001.andrewseanego.controller;

import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.repository.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.RecyclingTipRepository;
import com.enviro.assessment.grad001.andrewseanego.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicDataController {

    private final WasteCategoryRepository wasteCategoryRepository;
    private final DisposalGuidelineRepository disposalGuidelineRepository;
    private final RecyclingTipRepository recyclingTipRepository;

    @Autowired
    public PublicDataController(
            WasteCategoryRepository wasteCategoryRepository,
            DisposalGuidelineRepository disposalGuidelineRepository,
            RecyclingTipRepository recyclingTipRepository) {
        this.wasteCategoryRepository = wasteCategoryRepository;
        this.disposalGuidelineRepository = disposalGuidelineRepository;
        this.recyclingTipRepository = recyclingTipRepository;
    }

    @GetMapping("/waste-categories")
    public ResponseEntity<List<WasteCategory>> getWasteCategories() {
        return ResponseEntity.ok(wasteCategoryRepository.findAll());
    }

    @GetMapping("/disposal-guidelines")
    public ResponseEntity<List<DisposalGuideline>> getDisposalGuidelines() {
        return ResponseEntity.ok(disposalGuidelineRepository.findAll());
    }

    @GetMapping("/recycling-tips")
    public ResponseEntity<List<RecyclingTip>> getRecyclingTips() {
        return ResponseEntity.ok(recyclingTipRepository.findAll());
    }

    @GetMapping("/init-data")
    public ResponseEntity<String> initData() {
        // Create and save waste categories
        WasteCategory plastic = new WasteCategory();
        plastic.setName("Plastic");
        plastic.setDescription("Recyclable plastic materials");
        plastic.setRecyclable(true);
        plastic.setDisposalMethods(List.of("Landfill, Recycling plant"));

        WasteCategory paper = new WasteCategory();
        paper.setName("Paper");
        paper.setDescription("Paper and cardboard materials");
        paper.setRecyclable(true);
        paper.setDisposalMethods(List.of("Recycling plant, Composting"));

        WasteCategory glass = new WasteCategory();
        glass.setName("Glass");
        glass.setDescription("Glass bottles and containers");
        glass.setRecyclable(true);
        glass.setDisposalMethods(List.of("Recycling plant"));

        List<WasteCategory> savedCategories = wasteCategoryRepository.saveAll(Arrays.asList(plastic, paper, glass));
        System.out.println("Saved " + savedCategories.size() + " waste categories");

        // Create and save disposal guidelines
        DisposalGuideline plasticGuideline = new DisposalGuideline();
        plasticGuideline.setWasteCategory(plastic);
        plasticGuideline.setDescription("Proper disposal of plastic waste");
        plasticGuideline.setSteps("1. Rinse containers\n2. Remove caps\n3. Flatten bottles");
        plasticGuideline.setLegalRequirements("Separate recyclable plastics");

        DisposalGuideline paperGuideline = new DisposalGuideline();
        paperGuideline.setWasteCategory(paper);
        paperGuideline.setDescription("Eco-friendly paper waste disposal");
        paperGuideline.setSteps("1. Remove plastic wrapping\n2. Separate glossy magazines");
        paperGuideline.setLegalRequirements("Commercial recycling requirements");

        DisposalGuideline glassGuideline = new DisposalGuideline();
        glassGuideline.setWasteCategory(glass);
        glassGuideline.setDescription("Safe glass recycling");
        glassGuideline.setSteps("1. Rinse thoroughly\n2. Remove lids\n3. Sort by color");
        glassGuideline.setLegalRequirements("Sort by color in some regions");

        List<DisposalGuideline> savedGuidelines = disposalGuidelineRepository.saveAll(
                Arrays.asList(plasticGuideline, paperGuideline, glassGuideline));
        System.out.println("Saved " + savedGuidelines.size() + " disposal guidelines");

        // Create and save recycling tips
        RecyclingTip plasticTip = new RecyclingTip();
        plasticTip.setWasteCategory(plastic);
        plasticTip.setTitle("Plastic Number System");
        plasticTip.setDescription("Look for the number inside the recycling symbol");
        plasticTip.setSource("EPA");

        RecyclingTip paperTip = new RecyclingTip();
        paperTip.setWasteCategory(paper);
        paperTip.setTitle("Paper Recycling Water Savings");
        paperTip.setDescription("Recycling one ton of paper saves 7,000 gallons of water");
        paperTip.setSource("Paper Association");

        RecyclingTip glassTip = new RecyclingTip();
        glassTip.setWasteCategory(glass);
        glassTip.setTitle("Infinite Recyclability");
        glassTip.setDescription("Glass can be recycled indefinitely without loss of quality");
        glassTip.setSource("Glass Institute");

        List<RecyclingTip> savedTips = recyclingTipRepository.saveAll(
                Arrays.asList(plasticTip, paperTip, glassTip));
        System.out.println("Saved " + savedTips.size() + " recycling tips");

        return ResponseEntity.ok("Data initialized successfully: " + 
                savedCategories.size() + " categories, " + 
                savedGuidelines.size() + " guidelines, " + 
                savedTips.size() + " tips");
    }
}
