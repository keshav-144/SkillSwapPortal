package com.skillswap.controller.student.wanted;

import com.skillswap.dto.User;
import com.skillswap.service.WantedSkillService;
import com.skillswap.service.WantedSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/deleteWantedSkill")
public class DeleteWantedSkillServlet extends HttpServlet {
    private WantedSkillService wantedSkillService = new WantedSkillServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int wantedSkillId = Integer.parseInt(request.getParameter("wantedSkillId"));
        
        if (wantedSkillService.removeWantedSkill(wantedSkillId, sessionUser.getUserId())) {
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?success=Skill removed successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?error=Failed to remove skill.");
        }
    }
}
