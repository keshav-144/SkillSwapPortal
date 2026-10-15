package com.skillswap.service;

import com.skillswap.dto.Review;
import java.util.List;

public interface ReviewService {
    String submitReview(Review review);
    List<Review> getUserReviews(int userId);
}
