package com.skillswap.controller.student.report;

import com.skillswap.dto.Report;
import com.skillswap.dto.User;
import com.skillswap.service.ReportService;
import com.skillswap.service.ReportServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/submitReport")
public class SubmitReportServlet extends HttpServlet {
    private ReportService reportService = new ReportServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int reportedUserId = Integer.parseInt(request.getParameter("reportedUserId"));
        String reason = request.getParameter("reason");
        String description = request.getParameter("description");
        String returnUrl = request.getParameter("returnUrl");

        Report report = new Report();
        report.setReporterId(sessionUser.getUserId());
        report.setReportedUserId(reportedUserId);
        report.setReason(reason);
        report.setDescription(description);

        boolean success = reportService.submitUserReport(report);

        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = request.getContextPath() + "/student/activeExchanges";
        }

        if (success) {
            response.sendRedirect(returnUrl + (returnUrl.contains("?") ? "&" : "?") + "success=Report submitted successfully. Admin will review it.");
        } else {
            response.sendRedirect(returnUrl + (returnUrl.contains("?") ? "&" : "?") + "error=Failed to submit report.");
        }
    }
}
