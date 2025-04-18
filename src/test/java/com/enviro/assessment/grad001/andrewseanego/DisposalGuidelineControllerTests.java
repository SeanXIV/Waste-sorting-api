package com.enviro.assessment.grad001.andrewseanego;

import com.enviro.assessment.grad001.andrewseanego.controller.DisposalGuidelineController;
import com.enviro.assessment.grad001.andrewseanego.entity.DisposalGuideline;
//import com.enviro.assessment.grad001.andrewseanego.exception.ResourceNotFoundException;
import com.enviro.assessment.grad001.andrewseanego.exception.ValidationException;
import com.enviro.assessment.grad001.andrewseanego.service.DisposalGuidelineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DisposalGuidelineControllerTests {

	@Mock
	DisposalGuidelineService disposalGuidelineService;

	@InjectMocks
	DisposalGuidelineController disposalGuidelineController;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllDisposalGuidelines() {
		List<DisposalGuideline> disposalGuidelines = new ArrayList<>();
		DisposalGuideline guideline1 = new DisposalGuideline();
		guideline1.setId("1");
		guideline1.setDescription("Guideline 1");
		disposalGuidelines.add(guideline1);

		when(disposalGuidelineService.getAllDisposalGuidelines()).thenReturn(disposalGuidelines);

		ResponseEntity<List<DisposalGuideline>> responseEntity = disposalGuidelineController.getAllDisposalGuidelines();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(disposalGuidelines, responseEntity.getBody());
	}

	@Test
	public void testGetDisposalGuidelineById() {
		DisposalGuideline guideline = new DisposalGuideline();
		guideline.setId("1");
		guideline.setDescription("Guideline 1");

		when(disposalGuidelineService.getDisposalGuidelineById("1")).thenReturn(Optional.of(guideline));

		ResponseEntity<?> responseEntity = disposalGuidelineController.getDisposalGuidelineById("1");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(guideline, responseEntity.getBody());
	}

	@Test
	public void testGetDisposalGuidelineById_NotFound() {
		when(disposalGuidelineService.getDisposalGuidelineById("1")).thenReturn(Optional.empty());

		ResponseEntity<?> responseEntity = disposalGuidelineController.getDisposalGuidelineById("1");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testSaveDisposalGuideline() {
		// Create a mock BindingResult
		BindingResult bindingResult = mock(BindingResult.class);

		DisposalGuideline guideline = new DisposalGuideline();
		guideline.setId("1");
		guideline.setDescription("Guideline 1");

		when(disposalGuidelineService.saveDisposalGuideline(guideline)).thenReturn(guideline);

		ResponseEntity<DisposalGuideline> responseEntity = disposalGuidelineController.saveDisposalGuideline(guideline, bindingResult);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(guideline, responseEntity.getBody());
	}

	@Test
	public void testDeleteDisposalGuideline() {
		ResponseEntity<String> responseEntity = disposalGuidelineController.deleteDisposalGuideline("1");
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		verify(disposalGuidelineService, times(1)).deleteDisposalGuideline("1");
	}

	@Test
	public void testHandleValidationException() {
		ValidationException ex = new ValidationException("Validation error");

		ResponseEntity<String> responseEntity = disposalGuidelineController.handleValidationException(ex);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Validation error", responseEntity.getBody());
	}

}
