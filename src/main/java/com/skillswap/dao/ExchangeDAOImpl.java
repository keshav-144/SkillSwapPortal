package com.skillswap.dao;

import com.skillswap.dto.SkillExchange;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeDAOImpl implements ExchangeDAO {

    @Override
    public boolean acceptRequestAndCreateExchange(int requestId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            
            // BEGIN TRANSACTION (Step 33 Requirement)
            con.setAutoCommit(false);
            
            // 1. Fetch the request details
            String fetchSql = "SELECT sender_id, receiver_id, teach_skill_id, learn_skill_id FROM swap_requests WHERE request_id = ? AND status = 'PENDING'";
            int senderId = 0, receiverId = 0, teachSkillId = 0, learnSkillId = 0;
            
            try (PreparedStatement pstFetch = con.prepareStatement(fetchSql)) {
                pstFetch.setInt(1, requestId);
                try (ResultSet rs = pstFetch.executeQuery()) {
                    if (rs.next()) {
                        senderId = rs.getInt("sender_id");
                        receiverId = rs.getInt("receiver_id");
                        teachSkillId = rs.getInt("teach_skill_id");
                        learnSkillId = rs.getInt("learn_skill_id");
                    } else {
                        con.rollback();
                        return false; // Request not found or not pending
                    }
                }
            }

            // 2. Update Swap Request Status to ACCEPTED
            String updateSql = "UPDATE swap_requests SET status = 'ACCEPTED', responded_at = CURRENT_TIMESTAMP WHERE request_id = ?";
            try (PreparedStatement pstUpdate = con.prepareStatement(updateSql)) {
                pstUpdate.setInt(1, requestId);
                if (pstUpdate.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            // 3. Insert into Skill Exchanges
            String insertExchange = "INSERT INTO skill_exchanges (request_id, student_one_id, student_two_id, skill_one_id, skill_two_id, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
            try (PreparedStatement pstInsert = con.prepareStatement(insertExchange)) {
                pstInsert.setInt(1, requestId);
                pstInsert.setInt(2, senderId);
                pstInsert.setInt(3, receiverId);
                pstInsert.setInt(4, teachSkillId); // Skill sender teaches
                pstInsert.setInt(5, learnSkillId); // Skill receiver teaches
                
                if (pstInsert.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }
            
            // 4. Create Notification
            String insertNotif = "INSERT INTO notifications (user_id, title, message, notification_type) VALUES (?, 'Request Accepted', 'Your skill swap request was accepted!', 'SWAP_ACCEPT')";
            try (PreparedStatement pstNotif = con.prepareStatement(insertNotif)) {
                pstNotif.setInt(1, senderId);
                pstNotif.executeUpdate(); // Don't strictly fail transaction if notification fails, but in this case we'll keep it unified
            }

            // ALL QUERIES SUCCEEDED - COMMIT TRANSACTION
            con.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback(); // ROLLBACK ON ERROR
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return false;
    }

    @Override
    public List<SkillExchange> getMyExchanges(int userId) {
        List<SkillExchange> exchanges = new ArrayList<>();
        // Complex query to dynamically resolve 'Partner' vs 'Me'
        String sql = "SELECT se.*, " +
                     "CASE WHEN se.student_one_id = ? THEN u2.full_name ELSE u1.full_name END AS partner_name, " +
                     "CASE WHEN se.student_one_id = ? THEN s1.skill_name ELSE s2.skill_name END AS my_teaching_skill, " +
                     "CASE WHEN se.student_one_id = ? THEN s2.skill_name ELSE s1.skill_name END AS my_learning_skill " +
                     "FROM skill_exchanges se " +
                     "JOIN users u1 ON se.student_one_id = u1.user_id " +
                     "JOIN users u2 ON se.student_two_id = u2.user_id " +
                     "JOIN skills s1 ON se.skill_one_id = s1.skill_id " +
                     "JOIN skills s2 ON se.skill_two_id = s2.skill_id " +
                     "WHERE se.student_one_id = ? OR se.student_two_id = ? " +
                     "ORDER BY se.start_date DESC";
                     
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, userId);
            pst.setInt(2, userId);
            pst.setInt(3, userId);
            pst.setInt(4, userId);
            pst.setInt(5, userId);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SkillExchange se = new SkillExchange();
                    se.setExchangeId(rs.getInt("exchange_id"));
                    se.setRequestId(rs.getInt("request_id"));
                    se.setStudentOneId(rs.getInt("student_one_id"));
                    se.setStudentTwoId(rs.getInt("student_two_id"));
                    se.setStatus(rs.getString("status"));
                    se.setStartDate(rs.getTimestamp("start_date"));
                    se.setCompletedDate(rs.getTimestamp("completed_date"));
                    se.setPartnerName(rs.getString("partner_name"));
                    se.setMyTeachingSkill(rs.getString("my_teaching_skill"));
                    se.setMyLearningSkill(rs.getString("my_learning_skill"));
                    exchanges.add(se);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchanges;
    }

    @Override
    public boolean markExchangeCompleted(int exchangeId, int userId) {
        String sql = "UPDATE skill_exchanges SET status = 'COMPLETED', completed_date = CURRENT_TIMESTAMP " +
                     "WHERE exchange_id = ? AND (student_one_id = ? OR student_two_id = ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, exchangeId);
            pst.setInt(2, userId);
            pst.setInt(3, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
