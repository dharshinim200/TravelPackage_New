package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserRoleNotFound;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	Logger log =LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository repository;
    
    /**
     * Saves a new user record in the database.
     * @param user The user object to be saved.
     * @return Success message confirming the user has been saved.
     */
    @Override
    public String saveUser(User user){
    	log.info("In UserServiceImpl save user method ....");
        repository.save(user);
        return "Successfully saved";
        
    }

    /**
     * Updates an existing user record in the database.
     * @param user The user object containing updated details.
     * @return The updated user object.
     */
    @Override
    public User updateUser(User user) {
    	log.info("In UserServiceImpl update user method ....");
        return repository.save(user);
    }

    /**
     * Deletes a user from the database using their ID.
     * @param userId The ID of the user to be removed.
     * @return Success message confirming deletion.
     */
    @Override
    public String removeUser(int userId) {
    	log.info("In UserServiceImpl remove user method ....");
        repository.deleteById(userId);
        return "Deleted successfully";
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all users stored in the database.
     */
    @Override
    public List<User> getAllUser() {
    	log.info("In UserServiceImpl get all user method ....");
        return repository.findAll();
    }

    /**
     * Retrieves a user based on their ID.
     * @param userId The ID of the user to be fetched.
     * @return The user object if found.
     * @throws UserRoleNotFound 
     * @throws UserNotFound If the requested user does not exist.
     */
    @Override
    public User getUser(int userId) throws UserRoleNotFound{
    	log.info("In UserServiceImpl get by user id method ....");
        Optional<User> optional = repository.findById(userId);
        if(optional.isPresent()) {
        return optional.get();}
        else {
        	throw new UserRoleNotFound("User not found");
        }
    }

}
