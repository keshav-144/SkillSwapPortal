package com.skillswap.service;

import java.util.Map;
import com.skillswap.dto.Report;

public interface ReportService {
    Map<String, Integer> getSystemMetrics();
    boolean addNewMasterSkill(String category, String skillName);
    boolean submitUserReport(Report report);
}
