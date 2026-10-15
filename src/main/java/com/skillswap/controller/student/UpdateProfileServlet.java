package com.skillswap.controller.student;

import com.skillswap.dto.User;
import com.skillswap.service.UserService;
import com.skillswap.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { 
            response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); 
            return; 
        }

        // Business Rule: A student can only edit their own profile
        sessionUser.setFullName(request.getParameter("fullName"));
        sessionUser.setPhone(request.getParameter("phone"));
        sessionUser.setCollegeName(request.getParameter("collegeName"));
        sessionUser.setCourse(request.getParameter("course"));
        sessionUser.setYear(Integer.parseInt(request.getParameter("year")));
        sessionUser.setBio(request.getParameter("bio"));
        sessionUser.setLocation(request.getParameter("location"));

        if (userService.updateProfile(sessionUser)) {
            // Re-sync session state
            request.getSession().setAttribute("loggedInUser", sessionUser);
            response.sendRedirect(request.getContextPath() + "/student/profile?success=Profile updated successfully!");
        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/student/editProfile.jsp?error=Failed to update profile.");
        }
    }
}
