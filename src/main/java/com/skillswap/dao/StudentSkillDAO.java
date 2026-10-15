package com.skillswap.dao;

import com.skillswap.dto.StudentSkill;
import java.util.List;

public interface StudentSkillDAO {
    boolean addStudentSkill(StudentSkill skill);
    boolean deleteStudentSkill(int studentSkillId, int userId);
    List<StudentSkill> getSkillsByUserId(int userId);
    boolean checkDuplicateSkill(int userId, int skillId);
    boolean updateStudentSkill(int userId, int skillId, String skillLevel);
}
