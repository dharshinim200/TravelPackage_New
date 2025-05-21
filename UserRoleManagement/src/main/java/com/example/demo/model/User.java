package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class User {
	@Id
	private int userId;
	@Size(min = 3, message = "Name should have at least 3 characters")
	private String name;
	
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 4, message = "Password must be at least 4 characters")
	private String password;
	@NotBlank(message = "Role cannot be empty")
	private String role;

	@Column(name = "contact")
	private String contactNumber;
}
