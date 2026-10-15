package com.skillswap.controller.admin.student;

import com.skillswap.dao.UserDAO;
import com.skillswap.dao.UserDAOImpl;
import com.skillswap.dto.User;
import com.skillswap.service.StudentSkillService;
import com.skillswap.service.StudentSkillServiceImpl;
import com.skillswap.service.WantedSkillService;
import com.skillswap.service.WantedSkillServiceImpl;
import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.WantedSkill;
import com.skillswap.dto.SearchResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/admin/students")
public class ManageStudentsServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();
    private StudentSkillService teachService = new StudentSkillServiceImpl();
    private WantedSkillService wantService = new WantedSkillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); 
            return; 
        }

        List<User> allUsers = userDAO.getAllStudents();
        
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        int PAGE_SIZE = 5;
        int totalStudents = allUsers.size();
        int totalPages = (int) Math.ceil((double) totalStudents / PAGE_SIZE);
        
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalStudents);
        
        List<User> paginatedUsers = new ArrayList<>();
        if (start < totalStudents) {
            paginatedUsers = allUsers.subList(start, end);
        }
        
        List<SearchResult> studentCards = new ArrayList<>();
        
        for (User u : paginatedUsers) {
            SearchResult sr = new SearchResult();
            sr.setUserId(u.getUserId());
            sr.setFullName(u.getFullName());
            
            List<String> taught = teachService.getMySkills(u.getUserId()).stream()
                    .map(StudentSkill::getSkillName).collect(Collectors.toList());
            List<String> wanted = wantService.getMyWantedSkills(u.getUserId()).stream()
                    .map(WantedSkill::getSkillName).collect(Collectors.toList());
                    
            sr.setTaughtSkills(taught);
            sr.setWantedSkills(wanted);
            studentCards.add(sr);
        }
        
        request.setAttribute("students", studentCards);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/jsp/admin/students/manageStudents.jsp").forward(request, response);
    }
}
