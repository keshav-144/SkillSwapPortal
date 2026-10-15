package com.skillswap.dao;

import com.skillswap.dto.Admin;
import com.skillswap.util.DBConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin loginAdmin(String email, String password) {
        String sql = "SELECT * FROM admin WHERE email = ? AND password = ? AND status = 'ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, email);
            pst.setString(2, password);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setUserId(rs.getInt("admin_id"));
                    admin.setFullName(rs.getString("name"));
                    admin.setEmail(rs.getString("email"));
                    admin.setRole("ADMIN");
                    admin.setStatus(rs.getString("status"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Map<String, Integer> getDashboardMetrics() {
        Map<String, Integer> metrics = new HashMap<>();
        
        try (Connection con = DBConnection.getConnection()) {
            
            // 1. Total Students
            try (Statement st = con.createStatement(); 
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM users WHERE role='STUDENT'")) {
                if(rs.next()) metrics.put("totalStudents", rs.getInt(1));
            }
            
            // 2. Active Exchanges
            try (Statement st = con.createStatement(); 
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM skill_exchanges WHERE status='ACTIVE'")) {
                if(rs.next()) metrics.put("activeExchanges", rs.getInt(1));
            }
            
            // 3. Completed Exchanges
            try (Statement st = con.createStatement(); 
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM skill_exchanges WHERE status='COMPLETED'")) {
                if(rs.next()) metrics.put("completedExchanges", rs.getInt(1));
            }
            
            // 4. Total Skills Available
            try (Statement st = con.createStatement(); 
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM skills")) {
                if(rs.next()) metrics.put("totalSkills", rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metrics;
    }
    
    @Override
    public boolean addMasterSkill(String category, String skillName) {
        String sql = "INSERT INTO skills (category, skill_name) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, category);
            pst.setString(2, skillName);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
