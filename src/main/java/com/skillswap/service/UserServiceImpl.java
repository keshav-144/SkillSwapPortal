package com.skillswap.service;

import com.skillswap.dao.UserDAO;
import com.skillswap.dao.UserDAOImpl;
import com.skillswap.dto.User;
import com.skillswap.util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean registerUser(User user) {
        // Business Rule: Passwords must be securely hashed before reaching the DAO
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        return userDAO.registerUser(user);
    }

    @Override
    public User loginUser(String email, String password) {
        // Business Rule: Hash the login input to compare it against the stored hash
        String hashedPassword = PasswordUtil.hashPassword(password);
        return userDAO.loginUser(email, hashedPassword);
    }

    @Override
    public User getUserProfile(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public List<User> searchStudents(String keyword) {
        // Business Rule: If search is empty, fallback to returning all active students
        if(keyword == null || keyword.trim().isEmpty()) {
            return userDAO.getAllStudents();
        }
        return userDAO.searchUsers(keyword.trim());
    }

    @Override
    public boolean updateProfile(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userDAO.getUserByEmail(email.trim()) != null;
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        if (email == null || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        String hashedPassword = PasswordUtil.hashPassword(newPassword.trim());
        return userDAO.resetPassword(email.trim(), hashedPassword);
    }
}
