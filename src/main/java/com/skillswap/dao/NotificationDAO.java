package com.skillswap.dao;

import com.skillswap.dto.Notification;
import java.util.List;

public interface NotificationDAO {
    boolean createNotification(Notification notification);
    List<Notification> getNotificationsForUser(int userId);
    boolean markAllAsRead(int userId);
}
