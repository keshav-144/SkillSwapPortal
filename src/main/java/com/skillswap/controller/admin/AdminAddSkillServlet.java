package com.skillswap.controller.admin;

import com.skillswap.service.ReportService;
import com.skillswap.service.ReportServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/addSkill")
public class AdminAddSkillServlet extends HttpServlet {
    private ReportService reportService = new ReportServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        com.skillswap.dto.User sessionUser = (com.skillswap.dto.User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp");
            return;
        }

        String category = request.getParameter("category");
        String skillName = request.getParameter("skillName");
        
        boolean success = reportService.addNewMasterSkill(category, skillName);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/skills?success=Skill successfully added to the master catalog!");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/skills?error=Failed to add skill (may already exist).");
        }
    }
}
