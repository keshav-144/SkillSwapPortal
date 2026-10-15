package com.skillswap.controller.student.wanted;

import com.skillswap.dto.WantedSkill;
import com.skillswap.dto.Skill;
import com.skillswap.dto.User;
import com.skillswap.service.WantedSkillService;
import com.skillswap.service.WantedSkillServiceImpl;
import com.skillswap.dao.SkillDAO;
import com.skillswap.dao.SkillDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/wantedSkills")
public class WantedSkillsServlet extends HttpServlet {
    private WantedSkillService wantedSkillService = new WantedSkillServiceImpl();
    private SkillDAO skillDAO = new SkillDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        List<WantedSkill> wantedSkills = wantedSkillService.getMyWantedSkills(sessionUser.getUserId());
        request.setAttribute("wantedSkills", wantedSkills);
        
        List<Skill> allAvailableSkills = skillDAO.getAllSkills();
        request.setAttribute("allSkills", allAvailableSkills);

        request.getRequestDispatcher("/jsp/student/wanted/wantedSkills.jsp").forward(request, response);
    }
}
