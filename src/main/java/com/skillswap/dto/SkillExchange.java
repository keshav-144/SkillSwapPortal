package com.skillswap.dto;

import java.sql.Timestamp;

public class SkillExchange {
    private int exchangeId;
    private int requestId;
    private int studentOneId;
    private int studentTwoId;
    private int skillOneId;
    private int skillTwoId;
    private Timestamp startDate;
    private Timestamp expectedEndDate;
    private Timestamp completedDate;
    private String status; // ACTIVE, COMPLETED, CANCELLED
    
    // Virtual properties for UI rendering
    private String partnerName;
    private String myTeachingSkill;
    private String myLearningSkill;

    public SkillExchange() {}

    public int getExchangeId() { return exchangeId; }
    public void setExchangeId(int exchangeId) { this.exchangeId = exchangeId; }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getStudentOneId() { return studentOneId; }
    public void setStudentOneId(int studentOneId) { this.studentOneId = studentOneId; }

    public int getStudentTwoId() { return studentTwoId; }
    public void setStudentTwoId(int studentTwoId) { this.studentTwoId = studentTwoId; }

    public int getSkillOneId() { return skillOneId; }
    public void setSkillOneId(int skillOneId) { this.skillOneId = skillOneId; }

    public int getSkillTwoId() { return skillTwoId; }
    public void setSkillTwoId(int skillTwoId) { this.skillTwoId = skillTwoId; }

    public Timestamp getStartDate() { return startDate; }
    public void setStartDate(Timestamp startDate) { this.startDate = startDate; }

    public Timestamp getExpectedEndDate() { return expectedEndDate; }
    public void setExpectedEndDate(Timestamp expectedEndDate) { this.expectedEndDate = expectedEndDate; }

    public Timestamp getCompletedDate() { return completedDate; }
    public void setCompletedDate(Timestamp completedDate) { this.completedDate = completedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPartnerName() { return partnerName; }
    public void setPartnerName(String partnerName) { this.partnerName = partnerName; }

    public String getMyTeachingSkill() { return myTeachingSkill; }
    public void setMyTeachingSkill(String myTeachingSkill) { this.myTeachingSkill = myTeachingSkill; }

    public String getMyLearningSkill() { return myLearningSkill; }
    public void setMyLearningSkill(String myLearningSkill) { this.myLearningSkill = myLearningSkill; }
}
