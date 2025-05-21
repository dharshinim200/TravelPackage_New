package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pack {
	private int packageId;
	private String title;
	private String description;
	private int duration;
	private float price;
	private int availability;
	private String includedServices;
}
