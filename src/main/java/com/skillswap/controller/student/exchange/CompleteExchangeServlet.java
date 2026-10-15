package com.skillswap.controller.student.exchange;

import com.skillswap.dto.User;
import com.skillswap.service.ExchangeService;
import com.skillswap.service.ExchangeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/completeExchange")
public class CompleteExchangeServlet extends HttpServlet {
    private ExchangeService exchangeService = new ExchangeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        
        boolean success = exchangeService.markAsCompleted(exchangeId, sessionUser.getUserId());
        
        if (success) {
            // Redirect to leave a review!
            response.sendRedirect(request.getContextPath() + "/student/writeReview?exchangeId=" + exchangeId);
        } else {
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?error=Failed to complete exchange.");
        }
    }
}
