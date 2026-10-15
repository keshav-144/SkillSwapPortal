package com.skillswap.controller.auth;

import com.skillswap.service.UserService;
import com.skillswap.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Please provide your registered email address.");
            request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
            return;
        }

        email = email.trim();
        request.setAttribute("email", email);

        if (newPassword == null || newPassword.trim().length() < 6) {
            request.setAttribute("error", "New password must be at least 6 characters long.");
            request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
            return;
        }

        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match. Please ensure both fields are identical.");
            request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
            return;
        }

        if (!userService.isEmailRegistered(email)) {
            request.setAttribute("error", "No registered account found with email: " + email);
            request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
            return;
        }

        boolean resetSuccess = userService.resetPassword(email, newPassword);

        if (resetSuccess) {
            String successMsg = URLEncoder.encode("Password changed successfully! Please log in with your new password.", StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/login?success=" + successMsg);
        } else {
            request.setAttribute("error", "Failed to update password. Please try again later.");
            request.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(request, response);
        }
    }
}
