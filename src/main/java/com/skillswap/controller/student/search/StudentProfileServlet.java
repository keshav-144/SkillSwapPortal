package com.skillswap.controller.student.search;

import com.skillswap.dto.Review;
import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.User;
import com.skillswap.dto.WantedSkill;
import com.skillswap.service.ReviewService;
import com.skillswap.service.ReviewServiceImpl;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;
import com.skillswap.service.UserService;
import com.skillswap.service.UserServiceImpl;
import com.skillswap.service.WantedSkillService;
import com.skillswap.service.WantedSkillServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/viewProfile")
public class StudentProfileServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private StudentSkillService teachService = new StudentSkillServiceImpl();
    private WantedSkillService wantService = new WantedSkillServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enforce login
        if(request.getSession().getAttribute("loggedInUser") == null) { 
            response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); 
            return; 
        }

        int targetUserId = Integer.parseInt(request.getParameter("userId"));

        // Fetch all facets of the user's public persona
        User publicUser = userService.getUserProfile(targetUserId);
        if (publicUser == null) {
            response.sendRedirect(request.getContextPath() + "/student/search?error=User not found.");
            return;
        }

        List<StudentSkill> teachingSkills = teachService.getMySkills(targetUserId);
        List<WantedSkill> wantedSkills = wantService.getMyWantedSkills(targetUserId);
        List<Review> reviews = reviewService.getUserReviews(targetUserId);

        request.setAttribute("publicUser", publicUser);
        request.setAttribute("teachingSkills", teachingSkills);
        request.setAttribute("wantedSkills", wantedSkills);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("/jsp/student/search/studentProfile.jsp").forward(request, response);
    }
}
