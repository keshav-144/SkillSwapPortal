package com.skillswap.dao;

import com.skillswap.dto.StudentSkill;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSkillDAOImpl implements StudentSkillDAO {

    @Override
    public boolean addStudentSkill(StudentSkill skill) {
        String sql = "INSERT INTO student_skills (user_id, skill_id, skill_level, experience) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, skill.getUserId());
            pst.setInt(2, skill.getSkillId());
            pst.setString(3, skill.getSkillLevel());
            pst.setString(4, skill.getExperience());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStudentSkill(int studentSkillId, int userId) {
        String sql = "DELETE FROM student_skills WHERE student_skill_id = ? AND user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, studentSkillId);
            pst.setInt(2, userId);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<StudentSkill> getSkillsByUserId(int userId) {
        List<StudentSkill> skills = new ArrayList<>();
        String sql = "SELECT ss.*, s.skill_name, s.category FROM student_skills ss " +
                     "JOIN skills s ON ss.skill_id = s.skill_id " +
                     "WHERE ss.user_id = ? ORDER BY s.category, s.skill_name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    StudentSkill ss = new StudentSkill();
                    ss.setStudentSkillId(rs.getInt("student_skill_id"));
                    ss.setUserId(rs.getInt("user_id"));
                    ss.setSkillId(rs.getInt("skill_id"));
                    ss.setSkillLevel(rs.getString("skill_level"));
                    ss.setExperience(rs.getString("experience"));
                    ss.setCreatedAt(rs.getTimestamp("created_at"));
                    ss.setSkillName(rs.getString("skill_name"));
                    ss.setCategory(rs.getString("category"));
                    skills.add(ss);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public boolean checkDuplicateSkill(int userId, int skillId) {
        String sql = "SELECT student_skill_id FROM student_skills WHERE user_id = ? AND skill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            pst.setInt(2, skillId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentSkill(int userId, int skillId, String skillLevel) {
        String sql = "UPDATE student_skills SET skill_level = ? WHERE user_id = ? AND skill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, skillLevel);
            pst.setInt(2, userId);
            pst.setInt(3, skillId);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
