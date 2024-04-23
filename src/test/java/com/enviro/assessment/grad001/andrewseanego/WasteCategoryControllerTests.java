package com.enviro.assessment.grad001.andrewseanego;

import com.enviro.assessment.grad001.andrewseanego.controller.WasteCategoryController;
import com.enviro.assessment.grad001.andrewseanego.entity.WasteCategory;
import com.enviro.assessment.grad001.andrewseanego.service.WasteCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WasteCategoryControllerTests {

    @Mock
    WasteCategoryService wasteCategoryService;

    @InjectMocks
    WasteCategoryController wasteCategoryController;

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
        // Creating a mock BindingResult
        BindingResult bindingResult = mock(BindingResult.class);

        // Creating a test WasteCategory
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category 1");

        // Mocking the behavior of wasteCategoryService.saveWasteCategory
        when(wasteCategoryService.saveWasteCategory(any())).thenReturn(category);

        // Calling the controller method with the test WasteCategory and BindingResult
        ResponseEntity<WasteCategory> responseEntity = wasteCategoryController.saveWasteCategory(category, bindingResult);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
    }

    @Test
    public void testDeleteWasteCategory() {
        doNothing().when(wasteCategoryService).deleteWasteCategory(anyLong());

        ResponseEntity<String> responseEntity = wasteCategoryController.deleteWasteCategory(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(wasteCategoryService, times(1)).deleteWasteCategory(1L);
    }

    @Test
    public void testDeleteWasteCategory_Exception() {
        // Mock behavior to throw RuntimeException
        doThrow(RuntimeException.class).when(wasteCategoryService).deleteWasteCategory(anyLong());

        // Call the controller method and assert the exception
        ResponseEntity<String> responseEntity = wasteCategoryController.deleteWasteCategory(1L);

        // Verify that the service method was called
        verify(wasteCategoryService, times(1)).deleteWasteCategory(1L);

        // Assert the response status code
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
