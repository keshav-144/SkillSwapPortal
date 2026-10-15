package com.skillswap.service;

import com.skillswap.dao.SwapRequestDAO;
import com.skillswap.dao.SwapRequestDAOImpl;
import com.skillswap.dto.SwapRequest;

import java.util.List;

public class SwapRequestServiceImpl implements SwapRequestService {
    private SwapRequestDAO swapRequestDAO = new SwapRequestDAOImpl();

    @Override
    public String sendSwapRequest(SwapRequest request) {
        // Business Rule 1: A student cannot send a request to themselves.
        if (request.getSenderId() == request.getReceiverId()) {
            return "ERROR_SELF_REQUEST";
        }
        
        // Business Rule 2: A student cannot send another request if a previous request is already pending/accepted.
        boolean exists = swapRequestDAO.checkExistingRequest(
            request.getSenderId(), 
            request.getReceiverId(), 
            request.getTeachSkillId(), 
            request.getLearnSkillId()
        );
        
        if (exists) {
            return "ERROR_DUPLICATE";
        }
        
        // If all business checks pass, proceed to DAO
        boolean success = swapRequestDAO.sendRequest(request);
        return success ? "SUCCESS" : "ERROR_SYSTEM";
    }

    @Override
    public List<SwapRequest> getSentRequests(int senderId) {
        return swapRequestDAO.getSentRequests(senderId);
    }

    @Override
    public List<SwapRequest> getReceivedRequests(int receiverId) {
        return swapRequestDAO.getReceivedRequests(receiverId);
    }

    @Override
    public boolean respondToRequest(int requestId, String status) {
        // Future enhancement: If status == 'ACCEPTED', trigger Exchange creation transaction here.
        return swapRequestDAO.updateRequestStatus(requestId, status);
    }
}
