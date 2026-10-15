package com.skillswap.service;

import com.skillswap.dao.StudentSkillDAO;
import com.skillswap.dao.StudentSkillDAOImpl;
import com.skillswap.dto.StudentSkill;

import java.util.List;

public class StudentSkillServiceImpl implements StudentSkillService {
    private StudentSkillDAO skillDAO = new StudentSkillDAOImpl();

    @Override
    public String addSkill(StudentSkill skill) {
        if (skillDAO.checkDuplicateSkill(skill.getUserId(), skill.getSkillId())) {
            return "ERROR_DUPLICATE";
        }
        boolean success = skillDAO.addStudentSkill(skill);
        return success ? "SUCCESS" : "ERROR_SYSTEM";
    }

    @Override
    public boolean removeSkill(int studentSkillId, int userId) {
        return skillDAO.deleteStudentSkill(studentSkillId, userId);
    }

    @Override
    public List<StudentSkill> getMySkills(int userId) {
        return skillDAO.getSkillsByUserId(userId);
    }

    @Override
    public boolean updateSkill(int userId, int skillId, String skillLevel) {
        return skillDAO.updateStudentSkill(userId, skillId, skillLevel);
    }
}
