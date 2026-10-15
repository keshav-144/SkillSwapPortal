package com.skillswap.service;

import com.skillswap.dto.User;
import java.util.List;

public interface UserService {
    boolean registerUser(User user);
    User loginUser(String email, String password);
    User getUserProfile(int userId);
    List<User> searchStudents(String keyword);
    boolean updateProfile(User user);
    boolean isEmailRegistered(String email);
    boolean resetPassword(String email, String newPassword);
}
