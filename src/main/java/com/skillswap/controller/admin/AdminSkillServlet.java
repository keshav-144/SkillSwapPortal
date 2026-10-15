package com.skillswap.controller.admin;

import com.skillswap.service.SkillService;
import com.skillswap.service.SkillServiceImpl;
import com.skillswap.dto.Skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/skills")
public class AdminSkillServlet extends HttpServlet {
    private SkillService skillService = new SkillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enforce RBAC
        com.skillswap.dto.User sessionUser = (com.skillswap.dto.User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp");
            return;
        }

        List<Skill> allSkills = skillService.getAllMasterSkills();
        request.setAttribute("allSkills", allSkills);
        
        request.getRequestDispatcher("/jsp/admin/skills/skills.jsp").forward(request, response);
    }
}
