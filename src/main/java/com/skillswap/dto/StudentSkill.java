package com.skillswap.dto;

import java.sql.Timestamp;

public class StudentSkill {
    private int studentSkillId;
    private int userId;
    private int skillId;
    private String skillLevel; // BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    private String experience;
    private Timestamp createdAt;
    
    // UI Helper mapped via SQL JOIN
    private String skillName;
    private String category;

    public StudentSkill() {}

    public int getStudentSkillId() { return studentSkillId; }
    public void setStudentSkillId(int studentSkillId) { this.studentSkillId = studentSkillId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public String getSkillLevel() { return skillLevel; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
