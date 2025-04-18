package com.enviro.assessment.grad001.andrewseanego.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WasteCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Waste category must be provided")
    private WasteCategory wasteCategory;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @NotNull(message = "Collection date must be provided")
    private LocalDateTime collectionDate;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @NotNull(message = "Quantity must be provided")
    private Double quantity;

    private String unit;
}
