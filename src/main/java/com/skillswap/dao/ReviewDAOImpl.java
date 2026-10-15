package com.skillswap.dao;

import com.skillswap.dto.Review;
import com.skillswap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (exchange_id, reviewer_id, reviewed_student_id, rating, review_text) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, review.getExchangeId());
            pst.setInt(2, review.getReviewerId());
            pst.setInt(3, review.getRevieweeId());
            pst.setInt(4, review.getRating());
            pst.setString(5, review.getReviewText());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasUserReviewedExchange(int exchangeId, int reviewerId) {
        String sql = "SELECT review_id FROM reviews WHERE exchange_id = ? AND reviewer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, exchangeId);
            pst.setInt(2, reviewerId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // True if a review already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Review> getReviewsForUser(int revieweeId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name AS reviewer_name FROM reviews r " +
                     "JOIN users u ON r.reviewer_id = u.user_id " +
                     "WHERE r.reviewed_student_id = ? ORDER BY r.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, revieweeId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setReviewId(rs.getInt("review_id"));
                    r.setExchangeId(rs.getInt("exchange_id"));
                    r.setReviewerId(rs.getInt("reviewer_id"));
                    r.setRevieweeId(rs.getInt("reviewed_student_id"));
                    r.setRating(rs.getInt("rating"));
                    r.setReviewText(rs.getString("review_text"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    r.setReviewerName(rs.getString("reviewer_name"));
                    reviews.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
