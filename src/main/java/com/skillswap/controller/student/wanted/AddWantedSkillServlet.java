package com.skillswap.controller.student.wanted;

import com.skillswap.dto.User;
import com.skillswap.dto.WantedSkill;
import com.skillswap.service.WantedSkillService;
import com.skillswap.service.WantedSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/addWantedSkill")
public class AddWantedSkillServlet extends HttpServlet {
    private WantedSkillService wantedSkillService = new WantedSkillServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        String skillName = request.getParameter("skillName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        if (priority == null || priority.trim().isEmpty()) {
            priority = "MEDIUM"; // Default priority if not provided
        }

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
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?error=Failed to process skill.");
            return;
        }

        WantedSkill skill = new WantedSkill();
        skill.setUserId(sessionUser.getUserId());
        skill.setSkillId(skillId);
        skill.setPriority(priority);

        String result = wantedSkillService.addWantedSkill(skill);

        if ("SUCCESS".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?success=Skill added to your wishlist!");
        } else if ("ERROR_DUPLICATE".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?error=You have already added this skill.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/wantedSkills?error=Failed to add skill.");
        }
    }
}
