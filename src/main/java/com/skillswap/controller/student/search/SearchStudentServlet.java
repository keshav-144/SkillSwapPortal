package com.skillswap.controller.student.search;

import com.skillswap.dto.SearchResult;
import com.skillswap.dto.User;
import com.skillswap.service.SearchService;
import com.skillswap.service.SearchServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/search")
public class SearchStudentServlet extends HttpServlet {
    private SearchService searchService = new SearchServiceImpl();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }

        String skillKeyword = request.getParameter("query");
        String location = request.getParameter("location");
        String college = request.getParameter("college");
        
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        int offset = (page - 1) * PAGE_SIZE;

        List<SearchResult> results = searchService.searchAndMatch(
                sessionUser.getUserId(), skillKeyword, location, college, offset, PAGE_SIZE
        );
        
        int totalPages = searchService.getTotalSearchPages(sessionUser.getUserId(), skillKeyword, location, college, PAGE_SIZE);

        com.skillswap.dao.SwapRequestDAO requestDAO = new com.skillswap.dao.SwapRequestDAOImpl();
        List<com.skillswap.dto.SwapRequest> sentRequests = requestDAO.getSentRequests(sessionUser.getUserId());
        List<com.skillswap.dto.SwapRequest> receivedRequests = requestDAO.getReceivedRequests(sessionUser.getUserId());

        request.setAttribute("searchResults", results);
        request.setAttribute("sentRequests", sentRequests);
        request.setAttribute("receivedRequests", receivedRequests);
        request.setAttribute("query", skillKeyword);
        request.setAttribute("location", location);
        request.setAttribute("college", college);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("/jsp/student/search/searchStudents.jsp").forward(request, response);
    }
}
