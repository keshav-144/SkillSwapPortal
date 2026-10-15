package com.skillswap.service;

import com.skillswap.dao.NotificationDAO;
import com.skillswap.dao.NotificationDAOImpl;
import com.skillswap.dto.Notification;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public List<Notification> getAndMarkNotifications(int userId) {
        // Fetch them first so the UI knows which ones WERE unread for highlighting
        List<Notification> notifications = notificationDAO.getNotificationsForUser(userId);
        
        // Then mark them as read in the database
        notificationDAO.markAllAsRead(userId);
        
        return notifications;
    }

    @Override
    public boolean sendSystemNotification(int userId, String title, String message, String type) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setNotificationType(type);
        return notificationDAO.createNotification(n);
    }
}
