package com.skillswap.dao;

import com.skillswap.dto.SwapRequest;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SwapRequestDAOImpl implements SwapRequestDAO {

    @Override
    public boolean sendRequest(SwapRequest request) {
        String sql = "INSERT INTO swap_requests (sender_id, receiver_id, teach_skill_id, learn_skill_id, message) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, request.getSenderId());
            pst.setInt(2, request.getReceiverId());
            pst.setInt(3, request.getTeachSkillId());
            pst.setInt(4, request.getLearnSkillId());
            pst.setString(5, request.getMessage());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SwapRequest getRequestById(int requestId) {
        String sql = "SELECT * FROM swap_requests WHERE request_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, requestId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractSwapRequest(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SwapRequest> getSentRequests(int senderId) {
        List<SwapRequest> requests = new ArrayList<>();
        // Complex JOIN query to fetch human-readable names for the UI
        String sql = "SELECT sr.*, u.full_name as receiver_name, s1.skill_name as teach_skill_name, s2.skill_name as learn_skill_name " +
                     "FROM swap_requests sr " +
                     "JOIN users u ON sr.receiver_id = u.user_id " +
                     "JOIN skills s1 ON sr.teach_skill_id = s1.skill_id " +
                     "JOIN skills s2 ON sr.learn_skill_id = s2.skill_id " +
                     "WHERE sr.sender_id = ? ORDER BY sr.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, senderId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SwapRequest req = extractSwapRequest(rs);
                    req.setReceiverName(rs.getString("receiver_name"));
                    req.setTeachSkillName(rs.getString("teach_skill_name"));
                    req.setLearnSkillName(rs.getString("learn_skill_name"));
                    requests.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public List<SwapRequest> getReceivedRequests(int receiverId) {
        List<SwapRequest> requests = new ArrayList<>();
        String sql = "SELECT sr.*, u.full_name as sender_name, s1.skill_name as teach_skill_name, s2.skill_name as learn_skill_name " +
                     "FROM swap_requests sr " +
                     "JOIN users u ON sr.sender_id = u.user_id " +
                     "JOIN skills s1 ON sr.teach_skill_id = s1.skill_id " +
                     "JOIN skills s2 ON sr.learn_skill_id = s2.skill_id " +
                     "WHERE sr.receiver_id = ? ORDER BY sr.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, receiverId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    SwapRequest req = extractSwapRequest(rs);
                    req.setSenderName(rs.getString("sender_name"));
                    req.setTeachSkillName(rs.getString("teach_skill_name"));
                    req.setLearnSkillName(rs.getString("learn_skill_name"));
                    requests.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public boolean updateRequestStatus(int requestId, String status) {
        String sql = "UPDATE swap_requests SET status = ?, responded_at = CURRENT_TIMESTAMP WHERE request_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, status);
            pst.setInt(2, requestId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistingRequest(int senderId, int receiverId, int teachSkillId, int learnSkillId) {
        String sql = "SELECT request_id FROM swap_requests WHERE sender_id = ? AND receiver_id = ? " +
                     "AND teach_skill_id = ? AND learn_skill_id = ? AND status IN ('PENDING', 'ACCEPTED')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, senderId);
            pst.setInt(2, receiverId);
            pst.setInt(3, teachSkillId);
            pst.setInt(4, learnSkillId);
            
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Returns true if a match exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No existing request found
    }

    private SwapRequest extractSwapRequest(ResultSet rs) throws SQLException {
        SwapRequest request = new SwapRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setSenderId(rs.getInt("sender_id"));
        request.setReceiverId(rs.getInt("receiver_id"));
        request.setTeachSkillId(rs.getInt("teach_skill_id"));
        request.setLearnSkillId(rs.getInt("learn_skill_id"));
        request.setMessage(rs.getString("message"));
        request.setStatus(rs.getString("status"));
        request.setCreatedAt(rs.getTimestamp("created_at"));
        request.setRespondedAt(rs.getTimestamp("responded_at"));
        return request;
    }
}
