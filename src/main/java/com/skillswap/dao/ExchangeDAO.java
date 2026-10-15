package com.skillswap.dao;

import com.skillswap.dto.SkillExchange;
import java.util.List;

public interface ExchangeDAO {
    /**
     * Executes a strict JDBC Transaction to accept a swap request.
     * Updates request status -> Creates Exchange -> Creates Notification.
     */
    boolean acceptRequestAndCreateExchange(int requestId);
    
    /**
     * Retrieves all active/completed exchanges for a specific user.
     */
    List<SkillExchange> getMyExchanges(int userId);
    
    /**
     * Marks an ongoing exchange as COMPLETED.
     */
    boolean markExchangeCompleted(int exchangeId, int userId);
}
