package com.skillswap.controller.student.skill;

import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.Skill;
import com.skillswap.dto.User;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;
import com.skillswap.dao.SkillDAO;
import com.skillswap.dao.SkillDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/mySkills")
public class MySkillsServlet extends HttpServlet {
    private StudentSkillService studentSkillService = new StudentSkillServiceImpl();
    private SkillDAO skillDAO = new SkillDAOImpl(); // Simple direct read for dropdowns

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        // Fetch skills the student can teach
        List<StudentSkill> mySkills = studentSkillService.getMySkills(sessionUser.getUserId());
        request.setAttribute("mySkills", mySkills);
        
        // Fetch all available master skills for the dropdown in addSkill form
        List<Skill> allAvailableSkills = skillDAO.getAllSkills();
        request.setAttribute("allSkills", allAvailableSkills);

        request.getRequestDispatcher("/jsp/student/skills/mySkills.jsp").forward(request, response);
    }
}
