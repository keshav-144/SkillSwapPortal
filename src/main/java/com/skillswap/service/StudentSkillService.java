package com.skillswap.service;

import com.skillswap.dto.StudentSkill;
import java.util.List;

public interface StudentSkillService {
    String addSkill(StudentSkill skill);
    boolean removeSkill(int studentSkillId, int userId);
    List<StudentSkill> getMySkills(int userId);
    boolean updateSkill(int userId, int skillId, String skillLevel);
}
