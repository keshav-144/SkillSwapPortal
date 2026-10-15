package com.skillswap.controller.student.request;

import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.User;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;
import com.skillswap.dao.UserDAO;
import com.skillswap.dao.UserDAOImpl;
import com.skillswap.dao.SkillDAO;
import com.skillswap.dao.SkillDAOImpl;
import com.skillswap.dto.Skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/prepareRequest")
public class SendRequestPageServlet extends HttpServlet {
    private StudentSkillService studentSkillService = new StudentSkillServiceImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private SkillDAO skillDAO = new SkillDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int receiverId = Integer.parseInt(request.getParameter("receiverId"));

        User receiver = userDAO.getUserById(receiverId);
        
        // What receiver can teach (what I can learn)
        List<StudentSkill> receiverSkills = studentSkillService.getMySkills(receiverId);
        
        // What I can teach (what receiver can learn)
        List<StudentSkill> mySkills = studentSkillService.getMySkills(sessionUser.getUserId());

        request.setAttribute("receiver", receiver);
        request.setAttribute("receiverSkills", receiverSkills);
        request.setAttribute("mySkills", mySkills);
        
        request.getRequestDispatcher("/jsp/student/requests/sendRequest.jsp").forward(request, response);
    }
}
