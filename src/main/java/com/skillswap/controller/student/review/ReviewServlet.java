package com.skillswap.controller.student.review;

import com.skillswap.dto.User;
import com.skillswap.dao.ExchangeDAO;
import com.skillswap.dao.ExchangeDAOImpl;
import com.skillswap.dto.SkillExchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/writeReview")
public class ReviewServlet extends HttpServlet {
    private ExchangeDAO exchangeDAO = new ExchangeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        
        // Fetch exchange details to figure out who the reviewee is
        List<SkillExchange> myExchanges = exchangeDAO.getMyExchanges(sessionUser.getUserId());
        SkillExchange targetExchange = null;
        for (SkillExchange ex : myExchanges) {
            if (ex.getExchangeId() == exchangeId) {
                targetExchange = ex;
                break;
            }
        }
        
        if (targetExchange == null) {
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?error=Invalid exchange.");
            return;
        }
        
        // Determine the ID of the partner
        int partnerId = (targetExchange.getStudentOneId() == sessionUser.getUserId()) 
                        ? targetExchange.getStudentTwoId() 
                        : targetExchange.getStudentOneId();

        request.setAttribute("exchangeId", exchangeId);
        request.setAttribute("partnerId", partnerId);
        request.setAttribute("partnerName", targetExchange.getPartnerName());
        
        request.getRequestDispatcher("/jsp/student/reviews/addReview.jsp").forward(request, response);
    }
}
