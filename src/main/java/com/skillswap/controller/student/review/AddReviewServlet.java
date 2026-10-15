package com.skillswap.controller.student.review;

import com.skillswap.dto.Review;
import com.skillswap.dto.User;
import com.skillswap.service.ReviewService;
import com.skillswap.service.ReviewServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/submitReview")
public class AddReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        Review review = new Review();
        review.setExchangeId(Integer.parseInt(request.getParameter("exchangeId")));
        review.setReviewerId(sessionUser.getUserId());
        review.setRevieweeId(Integer.parseInt(request.getParameter("revieweeId")));
        review.setRating(Integer.parseInt(request.getParameter("rating")));
        review.setReviewText(request.getParameter("reviewText"));

        String result = reviewService.submitReview(review);

        if ("SUCCESS".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?success=Thank you! Your feedback has been recorded.");
        } else if ("ERROR_DUPLICATE".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?error=You have already reviewed this exchange.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?error=Failed to submit review.");
        }
    }
}
