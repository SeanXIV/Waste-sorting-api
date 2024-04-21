package com.enviro.assessment.grad001.andrewseanego;

import com.enviro.assessment.grad001.andrewseanego.controller.WasteCategoryController;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WasteCategoryControllerTests {

    @Mock
    WasteCategoryService wasteCategoryService;

    @InjectMocks
    WasteCategoryController wasteCategoryController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllWasteCategories() {
        List<WasteCategory> wasteCategories = new ArrayList<>();
        WasteCategory category1 = new WasteCategory();
        category1.setId(1L);
        category1.setName("Category 1");
        wasteCategories.add(category1);

        when(wasteCategoryService.getAllWasteCategories()).thenReturn(wasteCategories);

        ResponseEntity<List<WasteCategory>> responseEntity = wasteCategoryController.getAllWasteCategories();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wasteCategories, responseEntity.getBody());
    }

    @Test
    public void testGetWasteCategoryById() {
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category 1");

        when(wasteCategoryService.getWasteCategoryById(1L)).thenReturn(Optional.of(category));

        ResponseEntity<?> responseEntity = wasteCategoryController.getWasteCategoryById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    public void testGetWasteCategoryById_NotFound() {
        when(wasteCategoryService.getWasteCategoryById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = wasteCategoryController.getWasteCategoryById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveWasteCategory() {
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category 1");

        when(wasteCategoryService.saveWasteCategory(category)).thenReturn(category);

        ResponseEntity<WasteCategory> responseEntity = wasteCategoryController.saveWasteCategory(category);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    public void testDeleteWasteCategory() {
        ResponseEntity<String> responseEntity = wasteCategoryController.deleteWasteCategory(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(wasteCategoryService, times(1)).deleteWasteCategory(1L);
    }
}
