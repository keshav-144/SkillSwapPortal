package com.skillswap.service;

import com.skillswap.dao.MessageDAO;
import com.skillswap.dao.MessageDAOImpl;
import com.skillswap.dto.Message;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO = new MessageDAOImpl();

    @Override
    public boolean sendMessage(Message message) {
        // Business Rule: Ensure the sender is actually part of the exchange before saving
        if (!verifyAccess(message.getExchangeId(), message.getSenderId())) {
            return false;
        }
        return messageDAO.saveMessage(message);
    }

    @Override
    public List<Message> getChatHistory(int exchangeId, int userId) {
        if (!verifyAccess(exchangeId, userId)) {
            return null; // Return null to signify unauthorized access
        }
        
        // As a side-effect of loading the chat, mark unread messages from the partner as READ
        messageDAO.markMessagesAsRead(exchangeId, userId);
        
        return messageDAO.getMessagesForExchange(exchangeId);
    }

    @Override
    public boolean verifyAccess(int exchangeId, int userId) {
        return messageDAO.isUserPartOfExchange(exchangeId, userId);
    }
}
