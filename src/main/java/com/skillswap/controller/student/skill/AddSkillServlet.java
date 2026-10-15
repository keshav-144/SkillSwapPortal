package com.skillswap.controller.student.skill;

import com.skillswap.dto.Skill;
import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.User;
import com.skillswap.dao.SkillDAO;
import com.skillswap.dao.SkillDAOImpl;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/addSkill")
public class AddSkillServlet extends HttpServlet {
    private StudentSkillService studentSkillService = new StudentSkillServiceImpl();
    private SkillDAO skillDAO = new SkillDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }

        String skillName = request.getParameter("skillName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String proficiencyLevel = request.getParameter("proficiencyLevel");
        String experience = request.getParameter("experience");

        // 1. Check if skill exists in DB
        Skill existingSkill = skillDAO.getSkillByName(skillName);
        int skillId = -1;
        
        if (existingSkill != null) {
            skillId = existingSkill.getSkillId();
        } else {
            // Add new skill to DB
            Skill newSkill = new Skill();
            newSkill.setSkillName(skillName);
            newSkill.setCategory(category != null ? category : "Other");
            newSkill.setDescription(description != null ? description : "");
            skillId = skillDAO.addSkillAndGetId(newSkill);
        }

        if (skillId == -1) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Failed to process skill.");
            return;
        }

        StudentSkill studentSkill = new StudentSkill();
        studentSkill.setUserId(sessionUser.getUserId());
        studentSkill.setSkillId(skillId);
        studentSkill.setSkillLevel(proficiencyLevel);
        studentSkill.setExperience(experience);

        String result = studentSkillService.addSkill(studentSkill);
        
        if ("SUCCESS".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?success=Skill added successfully!");
        } else if ("ERROR_DUPLICATE".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=You already have this skill added.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Failed to add skill.");
        }
    }
}
