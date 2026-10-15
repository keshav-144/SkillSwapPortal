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

@WebServlet("/admin/editStudent")
public class EditStudentServlet extends HttpServlet {
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
            User student = userDAO.getUserById(studentId);
            if (student == null) {
                response.sendRedirect(request.getContextPath() + "/admin/students?error=Student not found.");
                return;
            }
            request.setAttribute("student", student);
            request.getRequestDispatcher("/jsp/admin/students/editStudent.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/students?error=Invalid student ID.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); 
            return; 
        }

        try {
            int studentId = Integer.parseInt(request.getParameter("id"));
            User student = userDAO.getUserById(studentId);
            
            if (student != null) {
                student.setFullName(request.getParameter("fullName"));
                student.setCollegeName(request.getParameter("collegeName"));
                student.setLocation(request.getParameter("location"));
                student.setStatus(request.getParameter("status"));
                
                boolean success = userDAO.updateUser(student);
                if (success) {
                    // Update status separately if there's a specific method, but updateUser might cover it.
                    // Wait, updateUser might not update status. Let's explicitly call updateStatus.
                    userDAO.updateStatus(studentId, request.getParameter("status"));
                    response.sendRedirect(request.getContextPath() + "/admin/students?success=Student updated successfully.");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/editStudent?id=" + studentId + "&error=Failed to update student.");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/students?error=Student not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/students?error=Invalid input.");
        }
    }
}
