package com.skillswap.service;

import com.skillswap.dto.Message;
import java.util.List;

public interface MessageService {
    boolean sendMessage(Message message);
    List<Message> getChatHistory(int exchangeId, int userId);
    boolean verifyAccess(int exchangeId, int userId);
}
