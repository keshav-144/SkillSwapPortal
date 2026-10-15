package com.skillswap.dao;

import com.skillswap.dto.Skill;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAOImpl implements SkillDAO {

    @Override
    public boolean addSkill(Skill skill) {
        String sql = "INSERT INTO skills (skill_name, category, description) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, skill.getSkillName());
            pst.setString(2, skill.getCategory());
            pst.setString(3, skill.getDescription());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Skill getSkillById(int skillId) {
        String sql = "SELECT * FROM skills WHERE skill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, skillId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractSkillFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills WHERE status = 'ACTIVE' ORDER BY category, skill_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                skills.add(extractSkillFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public List<Skill> getSkillsByCategory(String category) {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills WHERE category = ? AND status = 'ACTIVE' ORDER BY skill_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, category);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    skills.add(extractSkillFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public boolean updateSkillStatus(int skillId, String status) {
        String sql = "UPDATE skills SET status = ? WHERE skill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, status);
            pst.setInt(2, skillId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    @Override
    public int addSkillAndGetId(Skill skill) {
        String sql = "INSERT INTO skills (skill_name, category, description) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pst.setString(1, skill.getSkillName());
            pst.setString(2, skill.getCategory());
            pst.setString(3, skill.getDescription());
            
            if (pst.executeUpdate() > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Skill getSkillByName(String name) {
        String sql = "SELECT * FROM skills WHERE skill_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractSkillFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Skill extractSkillFromResultSet(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setSkillId(rs.getInt("skill_id"));
        skill.setSkillName(rs.getString("skill_name"));
        skill.setCategory(rs.getString("category"));
        skill.setDescription(rs.getString("description"));
        skill.setStatus(rs.getString("status"));
        skill.setCreatedAt(rs.getTimestamp("created_at"));
        return skill;
    }
}
