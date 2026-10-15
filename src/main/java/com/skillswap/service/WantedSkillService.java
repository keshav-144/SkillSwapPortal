package com.skillswap.service;

import com.skillswap.dto.WantedSkill;
import java.util.List;

public interface WantedSkillService {
    String addWantedSkill(WantedSkill skill);
    boolean removeWantedSkill(int wantedSkillId, int userId);
    List<WantedSkill> getMyWantedSkills(int userId);
}
