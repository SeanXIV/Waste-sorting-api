package com.enviro.assessment.grad001.andrewseanego;

import com.enviro.assessment.grad001.andrewseanego.controller.RecyclingTipController;
import com.enviro.assessment.grad001.andrewseanego.entity.RecyclingTip;
import com.enviro.assessment.grad001.andrewseanego.service.RecyclingTipService;
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

public class RecyclingTipControllerTests {

    @Mock
    RecyclingTipService recyclingTipService;

    @InjectMocks
    RecyclingTipController recyclingTipController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRecyclingTips() {
        List<RecyclingTip> recyclingTips = new ArrayList<>();
        RecyclingTip tip1 = new RecyclingTip();
        tip1.setId(1L);
        tip1.setTitle("Tip 1");
        recyclingTips.add(tip1);

        when(recyclingTipService.getAllRecyclingTips()).thenReturn(recyclingTips);

        ResponseEntity<List<RecyclingTip>> responseEntity = recyclingTipController.getAllRecyclingTips();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(recyclingTips, responseEntity.getBody());
    }

    @Test
    public void testGetRecyclingTipById() {
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTitle("Tip 1");

        when(recyclingTipService.getRecyclingTipById(1L)).thenReturn(Optional.of(tip));

        ResponseEntity<?> responseEntity = recyclingTipController.getRecyclingTipById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tip, responseEntity.getBody());
    }

    @Test
    public void testGetRecyclingTipById_NotFound() {
        when(recyclingTipService.getRecyclingTipById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = recyclingTipController.getRecyclingTipById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testSaveRecyclingTip() {
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTitle("Tip 1");

        when(recyclingTipService.saveRecyclingTip(tip)).thenReturn(tip);

        ResponseEntity<RecyclingTip> responseEntity = recyclingTipController.saveRecyclingTip(tip);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(tip, responseEntity.getBody());
    }

    @Test
    public void testDeleteRecyclingTip() {
        ResponseEntity<String> responseEntity = recyclingTipController.deleteRecyclingTip(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(recyclingTipService, times(1)).deleteRecyclingTip(1L);
    }
}
