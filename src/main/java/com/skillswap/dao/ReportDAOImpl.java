package com.skillswap.dao;

import com.skillswap.dto.Report;
import com.skillswap.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportDAOImpl implements ReportDAO {
    @Override
    public boolean submitReport(Report report) {
        String sql = "INSERT INTO reports (reporter_id, reported_user_id, reason, description, status) VALUES (?, ?, ?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, report.getReporterId());
            pst.setInt(2, report.getReportedUserId());
            pst.setString(3, report.getReason());
            pst.setString(4, report.getDescription());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
