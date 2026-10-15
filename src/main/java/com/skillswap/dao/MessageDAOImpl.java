package com.skillswap.dao;

import com.skillswap.dto.Message;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public boolean saveMessage(Message message) {
        String sql = "INSERT INTO messages (exchange_id, sender_id, receiver_id, message) " +
                     "SELECT ?, ?, " +
                     "CASE WHEN student_one_id = ? THEN student_two_id ELSE student_one_id END, ? " +
                     "FROM skill_exchanges WHERE exchange_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, message.getExchangeId());
            pst.setInt(2, message.getSenderId());
            pst.setInt(3, message.getSenderId());
            pst.setString(4, message.getMessageText());
            pst.setInt(5, message.getExchangeId());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Message> getMessagesForExchange(int exchangeId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.*, u.full_name FROM messages m " +
                     "JOIN users u ON m.sender_id = u.user_id " +
                     "WHERE m.exchange_id = ? ORDER BY m.sent_at ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, exchangeId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Message m = new Message();
                    m.setMessageId(rs.getInt("message_id"));
                    m.setExchangeId(rs.getInt("exchange_id"));
                    m.setSenderId(rs.getInt("sender_id"));
                    m.setMessageText(rs.getString("message"));
                    m.setSentAt(rs.getTimestamp("sent_at"));
                    m.setRead(rs.getBoolean("is_read"));
                    m.setSenderName(rs.getString("full_name"));
                    messages.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public boolean markMessagesAsRead(int exchangeId, int readerUserId) {
        // Marks messages as read IF the sender was NOT the person reading them
        String sql = "UPDATE messages SET is_read = TRUE WHERE exchange_id = ? AND sender_id != ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, exchangeId);
            pst.setInt(2, readerUserId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUserPartOfExchange(int exchangeId, int userId) {
        String sql = "SELECT exchange_id FROM skill_exchanges WHERE exchange_id = ? AND (student_one_id = ? OR student_two_id = ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, exchangeId);
            pst.setInt(2, userId);
            pst.setInt(3, userId);
            
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
