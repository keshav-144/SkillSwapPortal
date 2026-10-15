package com.skillswap.service;

import com.skillswap.dto.SwapRequest;
import java.util.List;

public interface SwapRequestService {
    /**
     * @return String status: "SUCCESS", "ERROR_SELF_REQUEST", "ERROR_DUPLICATE", or "ERROR_SYSTEM"
     */
    String sendSwapRequest(SwapRequest request);
    
    List<SwapRequest> getSentRequests(int senderId);
    
    List<SwapRequest> getReceivedRequests(int receiverId);
    
    boolean respondToRequest(int requestId, String status);
}
