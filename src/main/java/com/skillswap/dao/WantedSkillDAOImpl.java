package com.skillswap.dao;

import com.skillswap.dto.WantedSkill;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WantedSkillDAOImpl implements WantedSkillDAO {

    @Override
    public boolean addWantedSkill(WantedSkill skill) {
        String sql = "INSERT INTO wanted_skills (user_id, skill_id, priority) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, skill.getUserId());
            pst.setInt(2, skill.getSkillId());
            pst.setString(3, skill.getPriority());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteWantedSkill(int wantedSkillId, int userId) {
        // Enforcing userId check at DB level for security
        String sql = "DELETE FROM wanted_skills WHERE wanted_skill_id = ? AND user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, wantedSkillId);
            pst.setInt(2, userId);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<WantedSkill> getWantedSkillsByUserId(int userId) {
        List<WantedSkill> skills = new ArrayList<>();
        // INNER JOIN to fetch skill_name and category for the UI
        String sql = "SELECT ws.*, s.skill_name, s.category FROM wanted_skills ws " +
                     "JOIN skills s ON ws.skill_id = s.skill_id " +
                     "WHERE ws.user_id = ? ORDER BY s.category, s.skill_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    WantedSkill ws = new WantedSkill();
                    ws.setWantedSkillId(rs.getInt("wanted_skill_id"));
                    ws.setUserId(rs.getInt("user_id"));
                    ws.setSkillId(rs.getInt("skill_id"));
                    ws.setPriority(rs.getString("priority"));
                    ws.setCreatedAt(rs.getTimestamp("created_at"));
                    ws.setSkillName(rs.getString("skill_name"));
                    ws.setCategory(rs.getString("category"));
                    skills.add(ws);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public boolean checkDuplicateWantedSkill(int userId, int skillId) {
        String sql = "SELECT wanted_skill_id FROM wanted_skills WHERE user_id = ? AND skill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            pst.setInt(2, skillId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // True if record exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
