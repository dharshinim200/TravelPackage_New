package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.exception.UserRoleNotFound;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;
	

	@PostMapping("/saves")
	public String saveUser(@RequestBody @Validated User user) {
		return service.saveUser(user);
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody @Validated User user) {
		return service.updateUser(user);
	}

	@DeleteMapping("/deleteById/{did}")
	public String removeUser(@PathVariable("did") int userId) {
		return service.removeUser(userId);
	}

	@GetMapping("/fetchById/{fid}")
	public User getUser(@PathVariable("fid") int userId) throws UserRoleNotFound {
		return service.getUser(userId);
	}

	@GetMapping("/fetchAll")
	public List<User> getAllUser() {
		return service.getAllUser();
	}

}
