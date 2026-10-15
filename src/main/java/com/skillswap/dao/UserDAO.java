package com.skillswap.dao;

import com.skillswap.dto.User;
import java.util.List;

public interface UserDAO {
    /**
     * Registers a new user into the database.
     * @return boolean indicating success.
     */
    boolean registerUser(User user);
    
    /**
     * Verifies user credentials.
     * @return User object if successful, null if failed.
     */
    User loginUser(String email, String password);
    
    /**
     * Retrieves full user profile by ID.
     */
    User getUserById(int userId);
    
    /**
     * Updates an existing user's profile details.
     */
    boolean updateUser(User user);
    
    /**
     * Admin function: Returns all users registered as students.
     */
    List<User> getAllStudents();
    
    /**
     * Admin function: Blocks or activates a student.
     */
    boolean updateStatus(int userId, String status);
    
    /**
     * Searches users by keyword.
     */
    List<User> searchUsers(String keyword);
    
    /**
     * Admin function: Deletes a user completely.
     */
    boolean deleteUser(int userId);
    
    /**
     * Changes user password.
     */
    boolean changePassword(int userId, String newPassword);
    
    /**
     * Retrieves user profile by email address.
     */
    User getUserByEmail(String email);
    
    /**
     * Resets password using user's registered email.
     */
    boolean resetPassword(String email, String newPassword);
}
