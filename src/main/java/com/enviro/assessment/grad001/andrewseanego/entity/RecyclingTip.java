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
@Document(collection = "recycling_tips")
public class RecyclingTip {
    @Id
    private String id;

    @DocumentReference
    @NotNull(message = "Waste category must be provided")
    private WasteCategory wasteCategory;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Source cannot be blank")
    private String source;

    // Constructors, getters, and setters are handled by Lombok
}
