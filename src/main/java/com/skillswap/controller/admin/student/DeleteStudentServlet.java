package com.skillswap.controller.admin.student;

import com.skillswap.dao.UserDAO;
import com.skillswap.dao.UserDAOImpl;
import com.skillswap.dto.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); 
            return; 
        }

        try {
            int studentId = Integer.parseInt(request.getParameter("id"));
            boolean success = userDAO.deleteUser(studentId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/students?success=Student deleted successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/students?error=Failed to delete student.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/students?error=Invalid student ID.");
        }
    }
}
