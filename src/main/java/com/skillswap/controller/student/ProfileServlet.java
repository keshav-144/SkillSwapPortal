package com.skillswap.controller.student;

import com.skillswap.dto.User;
import com.skillswap.service.UserService;
import com.skillswap.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { 
            response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); 
            return; 
        }
        
        // Refresh user details from DB to get the latest stats
        User updatedUser = userService.getUserProfile(sessionUser.getUserId());
        
        com.skillswap.service.StudentSkillService teachService = new com.skillswap.service.StudentSkillServiceImpl();
        com.skillswap.service.WantedSkillService wantService = new com.skillswap.service.WantedSkillServiceImpl();
        
        java.util.List<com.skillswap.dto.StudentSkill> mySkills = teachService.getMySkills(sessionUser.getUserId());
        java.util.List<com.skillswap.dto.WantedSkill> wantedSkills = wantService.getMyWantedSkills(sessionUser.getUserId());
        
        request.setAttribute("userProfile", updatedUser);
        request.setAttribute("mySkills", mySkills);
        request.setAttribute("wantedSkills", wantedSkills);
        request.getRequestDispatcher("/jsp/student/profile/profile.jsp").forward(request, response);
    }
}
