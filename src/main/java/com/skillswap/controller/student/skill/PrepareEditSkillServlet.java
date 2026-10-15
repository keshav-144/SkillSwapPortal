package com.skillswap.controller.student.skill;

import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.User;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/prepareEditSkill")
public class PrepareEditSkillServlet extends HttpServlet {
    private StudentSkillService studentSkillService = new StudentSkillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }

        String skillIdParam = request.getParameter("skillId");
        if(skillIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills");
            return;
        }

        int skillId = Integer.parseInt(skillIdParam);
        List<StudentSkill> mySkills = studentSkillService.getMySkills(sessionUser.getUserId());
        
        StudentSkill skillToEdit = null;
        for (StudentSkill ss : mySkills) {
            if (ss.getSkillId() == skillId) {
                skillToEdit = ss;
                break;
            }
        }

        if (skillToEdit == null) {
            response.sendRedirect(request.getContextPath() + "/student/mySkills?error=Skill not found.");
            return;
        }

        request.setAttribute("skillToEdit", skillToEdit);
        request.getRequestDispatcher("/jsp/student/skills/editSkill.jsp").forward(request, response);
    }
}
