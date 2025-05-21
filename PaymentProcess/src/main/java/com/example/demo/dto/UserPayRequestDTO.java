package com.example.demo.dto;

import com.example.demo.model.Payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserPayRequestDTO {
  
	private User user;
	private Payment pay;
}
