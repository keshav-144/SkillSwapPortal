package com.skillswap.service;

import com.skillswap.dto.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getAndMarkNotifications(int userId);
    boolean sendSystemNotification(int userId, String title, String message, String type);
}
