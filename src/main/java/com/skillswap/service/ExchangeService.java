package com.skillswap.service;

import com.skillswap.dto.SkillExchange;
import java.util.List;

public interface ExchangeService {
    boolean acceptRequest(int requestId);
    List<SkillExchange> getMyActiveExchanges(int userId);
    boolean markAsCompleted(int exchangeId, int userId);
}
