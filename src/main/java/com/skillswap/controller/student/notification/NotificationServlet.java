package com.skillswap.controller.student.notification;

import com.skillswap.dto.Notification;
import com.skillswap.dto.User;
import com.skillswap.service.NotificationService;
import com.skillswap.service.NotificationServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/notifications")
public class NotificationServlet extends HttpServlet {
    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        List<Notification> notifications = notificationService.getAndMarkNotifications(sessionUser.getUserId());
        request.setAttribute("notifications", notifications);
        
        request.getRequestDispatcher("/jsp/student/notifications/notifications.jsp").forward(request, response);
    }
}
