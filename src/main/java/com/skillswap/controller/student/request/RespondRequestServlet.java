package com.skillswap.controller.student.request;

import com.skillswap.dto.User;
import com.skillswap.service.ExchangeService;
import com.skillswap.service.ExchangeServiceImpl;
import com.skillswap.service.SwapRequestService;
import com.skillswap.service.SwapRequestServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/respondRequest")
public class RespondRequestServlet extends HttpServlet {
    private ExchangeService exchangeService = new ExchangeServiceImpl();
    private SwapRequestService requestService = new SwapRequestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String status = request.getParameter("status"); // EXPECTED: ACCEPTED or REJECTED
        String action = request.getParameter("action"); // From searchStudents.jsp or receivedRequests.jsp

        if ("ACCEPTED".equals(status) || "accept".equals(action)) {
            // Fires the JDBC Transaction: Update Request -> Create Exchange -> Notify
            boolean success = exchangeService.acceptRequest(requestId);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/student/activeExchanges?success=Request Accepted! You are now connected.");
            } else {
                response.sendRedirect(request.getContextPath() + "/student/receivedRequests?error=Failed to accept request.");
            }
        } else if ("REJECTED".equals(status) || "reject".equals(action)) {
            boolean success = requestService.respondToRequest(requestId, "REJECTED");
            if (success) {
                response.sendRedirect(request.getContextPath() + "/student/receivedRequests?success=Request has been rejected.");
            } else {
                response.sendRedirect(request.getContextPath() + "/student/receivedRequests?error=Failed to reject request.");
            }
        } else if ("cancel".equals(action)) {
            boolean success = requestService.respondToRequest(requestId, "CANCELLED");
            if (success) {
                response.sendRedirect(request.getContextPath() + "/student/sentRequests?success=Request has been cancelled.");
            } else {
                response.sendRedirect(request.getContextPath() + "/student/sentRequests?error=Failed to cancel request.");
            }
        }
    }
}
