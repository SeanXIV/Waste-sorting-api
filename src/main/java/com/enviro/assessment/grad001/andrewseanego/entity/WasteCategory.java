package com.enviro.assessment.grad001.andrewseanego.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "waste_categories")
public class WasteCategory {
    @Id
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Recyclable flag is required")
    private boolean recyclable;

    private List<String> disposalMethods;

    // Constructors, getters, and setters are handled by Lombok
}
