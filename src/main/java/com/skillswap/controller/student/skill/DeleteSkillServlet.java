package com.skillswap.controller.student.skill;

import com.skillswap.dto.User;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/deleteSkill")
public class DeleteSkillServlet extends HttpServlet {
    private StudentSkillService skillService = new StudentSkillServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }

        int studentSkillId = Integer.parseInt(request.getParameter("studentSkillId"));
        
        // Pass userId alongside skillId to strictly ensure a user can only delete their OWN skills
        if (skillService.removeSkill(studentSkillId, sessionUser.getUserId())) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?success=Skill removed successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Failed to remove skill.");
        }
    }
}
