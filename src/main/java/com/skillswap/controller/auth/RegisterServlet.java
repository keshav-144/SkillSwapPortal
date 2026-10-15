package com.skillswap.controller.auth;

import com.skillswap.dto.User;
import com.skillswap.service.UserService;
import com.skillswap.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String collegeName = request.getParameter("collegeName");
        String course = request.getParameter("course");
        String yearStr = request.getParameter("year");
        String gender = request.getParameter("gender");

        // Server-Side Validation
        if (fullName == null || email == null || password == null || fullName.trim().isEmpty() || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/register?error=Validation failed. Name and Email are required.");
            return;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setCollegeName(collegeName);
        user.setCourse(course);
        user.setYear(yearStr != null && !yearStr.isEmpty() ? Integer.parseInt(yearStr) : 0);
        user.setGender(gender);
        user.setRole("STUDENT");
        user.setStatus("ACTIVE");

        boolean success = userService.registerUser(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?success=Registration successful! Please log in.");
        } else {
            response.sendRedirect(request.getContextPath() + "/register?error=Registration failed. Email might already exist.");
        }
    }
}
