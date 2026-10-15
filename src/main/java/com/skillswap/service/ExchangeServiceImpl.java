package com.skillswap.service;

import com.skillswap.dao.ExchangeDAO;
import com.skillswap.dao.ExchangeDAOImpl;
import com.skillswap.dto.SkillExchange;

import java.util.List;

public class ExchangeServiceImpl implements ExchangeService {
    private ExchangeDAO exchangeDAO = new ExchangeDAOImpl();

    @Override
    public boolean acceptRequest(int requestId) {
        return exchangeDAO.acceptRequestAndCreateExchange(requestId);
    }

    @Override
    public List<SkillExchange> getMyActiveExchanges(int userId) {
        return exchangeDAO.getMyExchanges(userId);
    }
    
    @Override
    public boolean markAsCompleted(int exchangeId, int userId) {
        return exchangeDAO.markExchangeCompleted(exchangeId, userId);
    }
}
