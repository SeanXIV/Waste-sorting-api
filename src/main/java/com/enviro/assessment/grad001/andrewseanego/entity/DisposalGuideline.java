package com.enviro.assessment.grad001.andrewseanego.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "disposal_guidelines")
public class DisposalGuideline {
    @Id
    private String id;

    @DocumentReference
    @NotNull(message = "Waste category must be provided")
    private WasteCategory wasteCategory;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotBlank(message = "Steps cannot be blank")
    private String steps;

    @NotBlank(message = "Legal requirements cannot be blank")
    private String legalRequirements;

    // Constructors, getters, and setters are handled by Lombok
}