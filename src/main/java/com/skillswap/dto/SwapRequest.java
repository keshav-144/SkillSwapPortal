package com.skillswap.dto;

import java.sql.Timestamp;

public class SwapRequest {
    private int requestId;
    private int senderId;
    private int receiverId;
    private int teachSkillId;
    private int learnSkillId;
    private String message;
    private String status; // PENDING, ACCEPTED, REJECTED, CANCELLED
    private Timestamp createdAt;
    private Timestamp respondedAt;
    
    // UI Helper properties that are joined from other tables
    private String senderName;
    private String receiverName;
    private String teachSkillName;
    private String learnSkillName;

    public SwapRequest() {}

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public int getTeachSkillId() { return teachSkillId; }
    public void setTeachSkillId(int teachSkillId) { this.teachSkillId = teachSkillId; }

    public int getLearnSkillId() { return learnSkillId; }
    public void setLearnSkillId(int learnSkillId) { this.learnSkillId = learnSkillId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getRespondedAt() { return respondedAt; }
    public void setRespondedAt(Timestamp respondedAt) { this.respondedAt = respondedAt; }

    // Getters and Setters for Helper Fields
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getTeachSkillName() { return teachSkillName; }
    public void setTeachSkillName(String teachSkillName) { this.teachSkillName = teachSkillName; }

    public String getLearnSkillName() { return learnSkillName; }
    public void setLearnSkillName(String learnSkillName) { this.learnSkillName = learnSkillName; }
}
