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
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "waste_collections")
public class WasteCollection {
    @Id
    private String id;

    @DocumentReference
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

    private String notes;

    @NotNull(message = "Quantity must be provided")
    private Double quantity;

    private String unit;
}
