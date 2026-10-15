<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Received Requests - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function toggleMenu() {
            document.getElementById('nav-links').classList.toggle('active');
        }
    </script>
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="menu-toggle" onclick="toggleMenu()">
            <span></span><span></span><span></span>
        </div>
        <div class="nav-links" id="nav-links">
            
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/search">See Students</a>
            <a href="${pageContext.request.contextPath}/student/mySkills">My Skills</a>
            <a href="${pageContext.request.contextPath}/student/receivedRequests">Requests</a>
            <a href="${pageContext.request.contextPath}/student/activeExchanges">Exchanges</a>
            <a href="${pageContext.request.contextPath}/student/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline" style="padding: 6px 16px;">Logout</a>
        
        </div>
    </nav>
    <main class="container animate-fade-in">
        <c:if test="${not empty error}">
            <div class="glass-panel" style="background: rgba(239, 68, 68, 0.1); border-color: rgba(239, 68, 68, 0.3); margin-bottom: 20px;">
                <p style="color: #f87171;">${error}</p>
            </div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="glass-panel" style="background: rgba(239, 68, 68, 0.1); border-color: rgba(239, 68, 68, 0.3); margin-bottom: 20px;">
                <p style="color: #f87171;">${param.error}</p>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="glass-panel" style="background: rgba(34, 197, 94, 0.1); border-color: rgba(34, 197, 94, 0.3); margin-bottom: 20px;">
                <p style="color: #4ade80;">${message}</p>
            </div>
        </c:if>
        <c:if test="${not empty param.success}">
            <div class="glass-panel" style="background: rgba(34, 197, 94, 0.1); border-color: rgba(34, 197, 94, 0.3); margin-bottom: 20px;">
                <p style="color: #4ade80;">${param.success}</p>
            </div>
        </c:if>
        
        
        <div class="flex justify-between items-center mb-4">
            <h1>Received Swap Requests</h1>
            <a href="${pageContext.request.contextPath}/student/sentRequests" class="btn btn-outline">View Sent Requests</a>
        </div>
        <div class="glass-panel">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>From</th>
                            <th>They Want</th>
                            <th>They Offer</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="req" items="${requests}">
                            <tr>
                                <td>${req.senderName}</td>
                                <td><span class="badge badge-warning">${req.learnSkillName}</span></td>
                                <td><span class="badge badge-success">${req.teachSkillName}</span></td>
                                <td><span class="badge" style="background: rgba(255,255,255,0.1);">${req.status}</span></td>
                                <td>
                                    <c:if test="${req.status == 'PENDING'}">
                                        <form action="${pageContext.request.contextPath}/student/respondRequest" method="post" style="display:inline;">
                                            <input type="hidden" name="requestId" value="${req.requestId}">
                                            <input type="hidden" name="action" value="accept">
                                            <button type="submit" class="btn btn-primary" style="padding: 4px 12px; font-size: 0.8rem;">Accept</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/student/respondRequest" method="post" style="display:inline;">
                                            <input type="hidden" name="requestId" value="${req.requestId}">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" class="btn btn-outline" style="padding: 4px 12px; font-size: 0.8rem; border-color: #ef4444; color: #ef4444;">Reject</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${req.status == 'ACCEPTED'}">
                                        <a href="${pageContext.request.contextPath}/student/activeExchanges" class="btn btn-primary" style="padding: 4px 12px; font-size: 0.8rem;">View Exchange</a>
                                    </c:if>
                                    <c:if test="${req.status != 'PENDING' && req.status != 'ACCEPTED'}">
                                        <span style="color: var(--text-muted); font-size: 0.8rem;">-</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        
        <c:if test="${totalPages > 1}">
            <div class="pagination flex justify-center gap-2 mt-4">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/student/receivedRequests?page=${i}" 
                       class="btn ${currentPage == i ? 'btn-primary' : 'btn-outline'}" 
                       style="padding: 6px 12px; font-size: 0.9rem;">${i}</a>
                </c:forEach>
            </div>
        </c:if>
    </main>
</body>
</html>