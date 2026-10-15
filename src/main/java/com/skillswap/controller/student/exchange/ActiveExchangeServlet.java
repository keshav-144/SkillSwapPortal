package com.skillswap.controller.student.exchange;

import com.skillswap.dto.SkillExchange;
import com.skillswap.dto.User;
import com.skillswap.service.ExchangeService;
import com.skillswap.service.ExchangeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/activeExchanges")
public class ActiveExchangeServlet extends HttpServlet {
    private ExchangeService exchangeService = new ExchangeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        List<SkillExchange> exchanges = exchangeService.getMyActiveExchanges(sessionUser.getUserId());
        request.setAttribute("exchanges", exchanges);
        
        request.getRequestDispatcher("/jsp/student/exchanges/activeExchanges.jsp").forward(request, response);
    }
}
