package com.skillswap.service;

import com.skillswap.dao.WantedSkillDAO;
import com.skillswap.dao.WantedSkillDAOImpl;
import com.skillswap.dto.WantedSkill;

import java.util.List;

public class WantedSkillServiceImpl implements WantedSkillService {
    private WantedSkillDAO skillDAO = new WantedSkillDAOImpl();

    @Override
    public String addWantedSkill(WantedSkill skill) {
        // Business Rule: Prevent duplicate wanted skills for the same student
        if (skillDAO.checkDuplicateWantedSkill(skill.getUserId(), skill.getSkillId())) {
            return "ERROR_DUPLICATE";
        }
        
        boolean success = skillDAO.addWantedSkill(skill);
        return success ? "SUCCESS" : "ERROR_SYSTEM";
    }

    @Override
    public boolean removeWantedSkill(int wantedSkillId, int userId) {
        return skillDAO.deleteWantedSkill(wantedSkillId, userId);
    }

    @Override
    public List<WantedSkill> getMyWantedSkills(int userId) {
        return skillDAO.getWantedSkillsByUserId(userId);
    }
}
