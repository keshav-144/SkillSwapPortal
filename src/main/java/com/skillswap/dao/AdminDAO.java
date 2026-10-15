package com.skillswap.dao;

import java.util.Map;
import com.skillswap.dto.Admin;

public interface AdminDAO {
    Admin loginAdmin(String email, String password);
    Map<String, Integer> getDashboardMetrics();
    boolean addMasterSkill(String category, String skillName);
}
