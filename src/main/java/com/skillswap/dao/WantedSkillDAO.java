package com.skillswap.dao;

import com.skillswap.dto.WantedSkill;
import java.util.List;

public interface WantedSkillDAO {
    boolean addWantedSkill(WantedSkill skill);
    boolean deleteWantedSkill(int wantedSkillId, int userId);
    List<WantedSkill> getWantedSkillsByUserId(int userId);
    boolean checkDuplicateWantedSkill(int userId, int skillId);
}
