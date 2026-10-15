package com.skillswap.dao;

import com.skillswap.dto.Skill;
import java.util.List;

public interface SkillDAO {
    /**
     * Adds a master skill entry.
     */
    boolean addSkill(Skill skill);
    int addSkillAndGetId(Skill skill);
    Skill getSkillByName(String name);
    
    /**
     * Retrieves specific skill details.
     */
    Skill getSkillById(int skillId);
    
    /**
     * Fetches all available skills across the platform.
     */
    List<Skill> getAllSkills();
    
    /**
     * Filters skills by category (e.g. 'Programming', 'Design').
     */
    List<Skill> getSkillsByCategory(String category);
    
    /**
     * Admin function: Safely update or disable a skill.
     */
    boolean updateSkillStatus(int skillId, String status);
}
