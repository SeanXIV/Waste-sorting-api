package com.enviro.assessment.grad001.andrewseanego.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DisposalGuideline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Waste category must be provided")
    private WasteCategory wasteCategory;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Steps cannot be blank")
    @Column(columnDefinition = "TEXT")
    private String steps;

    @NotBlank(message = "Legal requirements cannot be blank")
    @Column(columnDefinition = "TEXT")
    private String legalRequirements;

    // Constructors, getters, and setters are handled by Lombok
}