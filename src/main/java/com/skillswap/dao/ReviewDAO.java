package com.skillswap.dao;

import com.skillswap.dto.Review;
import java.util.List;

public interface ReviewDAO {
    boolean addReview(Review review);
    boolean hasUserReviewedExchange(int exchangeId, int reviewerId);
    List<Review> getReviewsForUser(int revieweeId);
}
