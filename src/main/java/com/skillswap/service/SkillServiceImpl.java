package com.skillswap.service;

import com.skillswap.dao.SkillDAO;
import com.skillswap.dao.SkillDAOImpl;
import com.skillswap.dto.Skill;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private SkillDAO skillDAO = new SkillDAOImpl();

    @Override
    public List<Skill> getAllMasterSkills() {
        return skillDAO.getAllSkills();
    }
}
