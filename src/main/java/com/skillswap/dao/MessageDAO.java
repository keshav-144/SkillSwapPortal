package com.skillswap.dao;

import com.skillswap.dto.Message;
import java.util.List;

public interface MessageDAO {
    boolean saveMessage(Message message);
    List<Message> getMessagesForExchange(int exchangeId);
    boolean markMessagesAsRead(int exchangeId, int readerUserId);
    
    // Security check: Ensures a user is actually part of this exchange before letting them view/send messages
    boolean isUserPartOfExchange(int exchangeId, int userId);
}
