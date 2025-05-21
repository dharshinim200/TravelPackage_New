package com.example.demo.dto;

import com.example.demo.model.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserPayRequestDTO {
   private Booking book;
   private Payment pay;
}
