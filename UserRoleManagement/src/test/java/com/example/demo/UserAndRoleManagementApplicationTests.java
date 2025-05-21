package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.exception.UserRoleNotFound;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;

@SpringBootTest
class UserAndRoleManagementApplicationTests {

	@Mock
	UserRepository repository;
	@InjectMocks
	UserServiceImpl service;

	@Test
	void saveUserTest() {
		User user = new User(113, "Ani", "Ani@gmail.com", "1234", "customer", "28342947");
		Mockito.when(repository.save(user)).thenReturn(user);
		String response = service.saveUser(user);
		assertEquals("successfully saved", response);
	}

	@Test
	void updateUserTest() {
		User user = new User(113, "Ani", "Ani@gmail.com", "1234", "customer", "28342947");
		Mockito.when(repository.save(user)).thenReturn(user);
		User updatedUser = service.updateUser(user);
		assertEquals(user, updatedUser);
	}

	@Test
	void removeUserTest() {
		int userId = 113;
		Mockito.doNothing().when(repository).deleteById(userId);
		String response = service.removeUser(userId);
		assertEquals("deleted successfully", response);
	}

	@Test
	void getAllUserTest() {
		List<User> mockUsers = Arrays.asList(new User(113, "Alice", "Ani@gmail.com", "1234", "customer", "28342947"),
				new User(113, "Bob", "Ani@gmail.com", "1234", "customer", "28342947"));

		Mockito.when(repository.findAll()).thenReturn(mockUsers);

		List<User> result = service.getAllUser();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Alice", result.get(0).getName());
		assertEquals("Bob", result.get(1).getName());
	}

	@Test
    void getUserSuccessTest() throws UserRoleNotFound {
		int userId = 113;
		User mockUser = new User(113, "Alice", "Alice@gmail.com", "1234", "customer", "28342947");

		Mockito.when(repository.findById(userId)).thenReturn(Optional.of(mockUser));

		User result = service.getUser(userId);

		assertNotNull(result);
		assertEquals(113, result.getUserId());
		assertEquals("Alice", result.getName());
	}


}
