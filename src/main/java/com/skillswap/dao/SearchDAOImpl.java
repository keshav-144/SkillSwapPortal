package com.skillswap.dao;

import com.skillswap.dto.SearchResult;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchDAOImpl implements SearchDAO {

    @Override
    public List<SearchResult> searchStudents(int searcherId, String skillKeyword, String location, String college, int offset, int limit) {
        List<SearchResult> results = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT u.user_id, u.full_name, u.college_name, u.location, u.gender " +
            "FROM users u " +
            "LEFT JOIN student_skills ss ON u.user_id = ss.user_id " +
            "LEFT JOIN skills s ON ss.skill_id = s.skill_id " +
            "WHERE u.user_id != ? AND u.status = 'ACTIVE' "
        );
        
        if (skillKeyword != null && !skillKeyword.isEmpty()) {
            sql.append("AND (s.skill_name LIKE ? OR u.full_name LIKE ?) ");
        }
        if (location != null && !location.isEmpty()) {
            sql.append("AND u.location LIKE ? ");
        }
        if (college != null && !college.isEmpty()) {
            sql.append("AND u.college_name LIKE ? ");
        }
        
        sql.append(" LIMIT ? OFFSET ?");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql.toString())) {
             
            int paramIndex = 1;
            pst.setInt(paramIndex++, searcherId);
            
            if (skillKeyword != null && !skillKeyword.isEmpty()) {
                pst.setString(paramIndex++, "%" + skillKeyword + "%");
                pst.setString(paramIndex++, "%" + skillKeyword + "%");
            }
            if (location != null && !location.isEmpty()) {
                pst.setString(paramIndex++, "%" + location + "%");
            }
            if (college != null && !college.isEmpty()) {
                pst.setString(paramIndex++, "%" + college + "%");
            }
            
            pst.setInt(paramIndex++, limit);
            pst.setInt(paramIndex++, offset);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SearchResult sr = new SearchResult();
                    sr.setUserId(rs.getInt("user_id"));
                    sr.setFullName(rs.getString("full_name"));
                    sr.setCollegeName(rs.getString("college_name"));
                    sr.setLocation(rs.getString("location"));
                    sr.setGender(rs.getString("gender"));
                    results.add(sr);
                }
            }
            
            // Now populate skills
            String taughtSql = "SELECT s.skill_name FROM student_skills ss JOIN skills s ON ss.skill_id = s.skill_id WHERE ss.user_id = ?";
            String wantedSql = "SELECT s.skill_name FROM wanted_skills ws JOIN skills s ON ws.skill_id = s.skill_id WHERE ws.user_id = ?";
            
            try (PreparedStatement pTaught = con.prepareStatement(taughtSql);
                 PreparedStatement pWanted = con.prepareStatement(wantedSql)) {
                
                for (SearchResult sr : results) {
                    pTaught.setInt(1, sr.getUserId());
                    try (ResultSet rsT = pTaught.executeQuery()) {
                        while (rsT.next()) {
                            sr.getTaughtSkills().add(rsT.getString("skill_name"));
                        }
                    }
                    pWanted.setInt(1, sr.getUserId());
                    try (ResultSet rsW = pWanted.executeQuery()) {
                        while (rsW.next()) {
                            sr.getWantedSkills().add(rsW.getString("skill_name"));
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int countStudents(int searcherId, String skillKeyword, String location, String college) {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(DISTINCT u.user_id) " +
            "FROM users u " +
            "LEFT JOIN student_skills ss ON u.user_id = ss.user_id " +
            "LEFT JOIN skills s ON ss.skill_id = s.skill_id " +
            "WHERE u.user_id != ? AND u.status = 'ACTIVE' "
        );
        
        if (skillKeyword != null && !skillKeyword.isEmpty()) {
            sql.append("AND (s.skill_name LIKE ? OR u.full_name LIKE ?) ");
        }
        if (location != null && !location.isEmpty()) {
            sql.append("AND u.location LIKE ? ");
        }
        if (college != null && !college.isEmpty()) {
            sql.append("AND u.college_name LIKE ? ");
        }
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql.toString())) {
             
            int paramIndex = 1;
            pst.setInt(paramIndex++, searcherId);
            
            if (skillKeyword != null && !skillKeyword.isEmpty()) {
                pst.setString(paramIndex++, "%" + skillKeyword + "%");
                pst.setString(paramIndex++, "%" + skillKeyword + "%");
            }
            if (location != null && !location.isEmpty()) {
                pst.setString(paramIndex++, "%" + location + "%");
            }
            if (college != null && !college.isEmpty()) {
                pst.setString(paramIndex++, "%" + college + "%");
            }
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
