package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="package_info")
public class Pack {
    @Id
	private int packageId;
    @NotBlank(message = "Title cannot be empty")
	private String title;
    @NotBlank(message = "Description cannot be empty")
	private String description;
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration must be within one year")
	private String duration;
	@Min(value = 0, message = "Price must be non-negative")
	private float price;
	@Min(value = 0, message = "availablity must be non-negative")
	private int availability;
	@NotBlank(message = "Included services cannot be empty")
	private String includedServices;
}
