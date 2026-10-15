package com.skillswap.dto;

import java.sql.Timestamp;

public class WantedSkill {
    private int wantedSkillId;
    private int userId;
    private int skillId;
    private String priority; // LOW, MEDIUM, HIGH
    private Timestamp createdAt;
    
    // UI Helper properties populated via SQL JOIN
    private String skillName;
    private String category;

    public WantedSkill() {}

    public int getWantedSkillId() { return wantedSkillId; }
    public void setWantedSkillId(int wantedSkillId) { this.wantedSkillId = wantedSkillId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
