package com.skillswap.controller.admin;

import com.skillswap.service.ReportService;
import com.skillswap.service.ReportServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private ReportService reportService = new ReportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enforce RBAC
        com.skillswap.dto.User sessionUser = (com.skillswap.dto.User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null || !"ADMIN".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Map<String, Integer> metrics = reportService.getSystemMetrics();
        request.setAttribute("metrics", metrics);
        
        request.getRequestDispatcher("/jsp/admin/dashboard.jsp").forward(request, response);
    }
}
