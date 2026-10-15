package com.skillswap.service;

import com.skillswap.dao.AdminDAO;
import com.skillswap.dao.AdminDAOImpl;

import com.skillswap.dao.ReportDAO;
import com.skillswap.dao.ReportDAOImpl;
import com.skillswap.dto.Report;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private AdminDAO adminDAO = new AdminDAOImpl();
    private ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    public Map<String, Integer> getSystemMetrics() {
        // Here we could add business logic, like caching the metrics if they get too heavy,
        // or logging admin access for audit purposes.
        return adminDAO.getDashboardMetrics();
    }

    @Override
    public boolean addNewMasterSkill(String category, String skillName) {
        // Business Logic Validation: Ensure neither field is empty
        if (category == null || category.trim().isEmpty() || skillName == null || skillName.trim().isEmpty()) {
            return false;
        }
        
        return adminDAO.addMasterSkill(category.trim(), skillName.trim());
    }

    @Override
    public boolean submitUserReport(Report report) {
        if (report == null || report.getReason() == null || report.getReason().trim().isEmpty()) {
            return false;
        }
        return reportDAO.submitReport(report);
    }
}
