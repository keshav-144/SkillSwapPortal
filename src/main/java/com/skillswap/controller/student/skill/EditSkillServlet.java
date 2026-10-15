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

@WebServlet("/student/editSkill")
public class EditSkillServlet extends HttpServlet {
    private StudentSkillService skillService = new StudentSkillServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }

        int studentSkillId = Integer.parseInt(request.getParameter("studentSkillId"));
        String skillName = request.getParameter("skillName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String proficiencyLevel = request.getParameter("proficiencyLevel");
        String experience = request.getParameter("experience");

        // Remove old mapping
        skillService.removeSkill(studentSkillId, sessionUser.getUserId());

        // Process like a new add
        com.skillswap.dao.SkillDAO skillDAO = new com.skillswap.dao.SkillDAOImpl();
        com.skillswap.dto.Skill existingSkill = skillDAO.getSkillByName(skillName);
        int skillId = -1;
        
        if (existingSkill != null) {
            skillId = existingSkill.getSkillId();
        } else {
            com.skillswap.dto.Skill newSkill = new com.skillswap.dto.Skill();
            newSkill.setSkillName(skillName);
            newSkill.setCategory(category != null ? category : "Other");
            newSkill.setDescription(description != null ? description : "");
            skillId = skillDAO.addSkillAndGetId(newSkill);
        }

        if (skillId == -1) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Failed to process skill update.");
            return;
        }

        com.skillswap.dto.StudentSkill studentSkill = new com.skillswap.dto.StudentSkill();
        studentSkill.setUserId(sessionUser.getUserId());
        studentSkill.setSkillId(skillId);
        studentSkill.setSkillLevel(proficiencyLevel);
        studentSkill.setExperience(experience);

        String result = skillService.addSkill(studentSkill);
        
        if ("SUCCESS".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?success=Skill updated successfully!");
        } else if ("ERROR_DUPLICATE".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=You already have this skill added.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Failed to update skill.");
        }
    }
}
