package com.skillswap.controller.student.message;

import com.skillswap.dto.Message;
import com.skillswap.dto.User;
import com.skillswap.dto.SkillExchange;
import com.skillswap.service.MessageService;
import com.skillswap.service.MessageServiceImpl;
import com.skillswap.service.ExchangeService;
import com.skillswap.service.ExchangeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/messages")
public class MessageServlet extends HttpServlet {
    private MessageService messageService = new MessageServiceImpl();
    private ExchangeService exchangeService = new ExchangeServiceImpl();

    // GET: Loads the chat UI and history
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        
        List<Message> chatHistory = messageService.getChatHistory(exchangeId, sessionUser.getUserId());
        
        if (chatHistory == null) {
            // Null implies verification failed. Prevent snooping on other people's chats!
            response.sendRedirect(request.getContextPath() + "/student/activeExchanges?error=Unauthorized access to chat.");
            return;
        }

        List<SkillExchange> conversations = exchangeService.getMyActiveExchanges(sessionUser.getUserId());
        request.setAttribute("conversations", conversations);
        
        for (SkillExchange ex : conversations) {
            if (ex.getExchangeId() == exchangeId) {
                int partnerId = (ex.getStudentOneId() == sessionUser.getUserId()) 
                                ? ex.getStudentTwoId() : ex.getStudentOneId();
                request.setAttribute("partnerId", partnerId);
                break;
            }
        }
        
        request.setAttribute("chatHistory", chatHistory);
        request.setAttribute("exchangeId", exchangeId);
        
        request.getRequestDispatcher("/jsp/student/messages/messages.jsp").forward(request, response);
    }

    // POST: Send a new message
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        String text = request.getParameter("messageText");

        if (text != null && !text.trim().isEmpty()) {
            Message msg = new Message();
            msg.setExchangeId(exchangeId);
            msg.setSenderId(sessionUser.getUserId());
            msg.setMessageText(text.trim());
            
            messageService.sendMessage(msg);
        }

        // Post-Redirect-Get back to the chat view
        response.sendRedirect(request.getContextPath() + "/student/messages?exchangeId=" + exchangeId);
    }
}
