package com.enviro.assessment.grad001.andrewseanego.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recycling_centers")
public class RecyclingCenter {
    @Id
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Contact information is required")
    @Size(max = 255, message = "Contact information cannot exceed 255 characters")
    private String contactInfo;

    private String description;

    @DocumentReference
    private List<WasteCategory> acceptedWasteCategories;

    private String operatingHours;

    private boolean active;
}
