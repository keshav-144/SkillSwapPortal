package com.skillswap.controller.student.request;

import com.skillswap.dto.SwapRequest;
import com.skillswap.dto.User;
import com.skillswap.service.SwapRequestService;
import com.skillswap.service.SwapRequestServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/sendSwapRequest")
public class SendSwapRequestServlet extends HttpServlet {
    private SwapRequestService requestService = new SwapRequestServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if(sessionUser == null) { response.sendRedirect(request.getContextPath() + "/jsp/auth/login.jsp"); return; }

        SwapRequest swapReq = new SwapRequest();
        swapReq.setSenderId(sessionUser.getUserId());
        swapReq.setReceiverId(Integer.parseInt(request.getParameter("receiverId")));
        swapReq.setLearnSkillId(Integer.parseInt(request.getParameter("learnSkillId")));
        swapReq.setTeachSkillId(Integer.parseInt(request.getParameter("teachSkillId")));
        swapReq.setMessage(request.getParameter("message"));

        String result = requestService.sendSwapRequest(swapReq);

        if ("SUCCESS".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/sentRequests?success=Skill swap request sent successfully!");
        } else if ("ERROR_DUPLICATE".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/search?error=You already have a pending or accepted request with this user for these skills.");
        } else if ("ERROR_SELF_REQUEST".equals(result)) {
            response.sendRedirect(request.getContextPath() + "/student/search?error=You cannot send a request to yourself.");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/search?error=System error occurred while sending request.");
        }
    }
}
