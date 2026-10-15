package com.skillswap.service;

import com.skillswap.dao.ReviewDAO;
import com.skillswap.dao.ReviewDAOImpl;
import com.skillswap.dto.Review;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAOImpl();

    @Override
    public String submitReview(Review review) {
        // Validation Rule 1: Rating must be 1 to 5
        if (review.getRating() < 1 || review.getRating() > 5) {
            return "ERROR_INVALID_RATING";
        }
        
        // Validation Rule 2: Prevent duplicate reviews for the same exchange
        if (reviewDAO.hasUserReviewedExchange(review.getExchangeId(), review.getReviewerId())) {
            return "ERROR_DUPLICATE";
        }
        
        boolean success = reviewDAO.addReview(review);
        return success ? "SUCCESS" : "ERROR_SYSTEM";
    }

    @Override
    public List<Review> getUserReviews(int userId) {
        return reviewDAO.getReviewsForUser(userId);
    }
}
