package com.skillswap.controller.student.request;

import com.skillswap.dto.SwapRequest;
import com.skillswap.dto.User;
import com.skillswap.service.SwapRequestService;
import com.skillswap.service.SwapRequestServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/sentRequests")
public class SentRequestsServlet extends HttpServlet {
    private SwapRequestService requestService = new SwapRequestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        List<SwapRequest> allRequests = requestService.getSentRequests(sessionUser.getUserId());
        
        int page = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        int totalRequests = allRequests.size();
        int totalPages = (int) Math.ceil((double) totalRequests / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;
        if (page < 1) page = 1;
        
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalRequests);
        List<SwapRequest> paginatedRequests = allRequests.subList(start, end);

        request.setAttribute("requests", paginatedRequests);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("/jsp/student/requests/sentRequests.jsp").forward(request, response);
    }
}
