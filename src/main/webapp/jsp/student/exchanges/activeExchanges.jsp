<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Active Exchanges - SkillSwap</title>
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
        
        
        <h1>Active Exchanges</h1>
        <div class="grid grid-cols-2 mt-4">
            <c:forEach var="exchange" items="${exchanges}">
                <div class="glass-panel text-center">
                    <h3 style="color: var(--primary); margin-bottom: 10px;">Connected with ${exchange.partnerName}</h3>
                    <div style="background: rgba(255,255,255,0.05); border-radius: 8px; padding: 15px; text-align: left; margin: 15px 0;">
                        <p style="margin-bottom: 8px;"><strong>Status:</strong> <span class="badge" style="background: rgba(34,197,94,0.2); color: #4ade80;">${exchange.status}</span></p>
                        <p style="margin-bottom: 8px;"><strong>You Teach:</strong> <span style="color: #4ade80; font-weight: 500;">${exchange.myTeachingSkill}</span></p>
                        <p style="margin-bottom: 8px;"><strong>You Learn:</strong> <span style="color: #ec4899; font-weight: 500;">${exchange.myLearningSkill}</span></p>
                        <p style="font-size: 0.85rem; color: var(--text-muted); margin-top: 10px;">Started: ${exchange.startDate}</p>
                    </div>
                    <div class="flex gap-4 mt-4 justify-center">
                        <a href="${pageContext.request.contextPath}/student/messages?exchangeId=${exchange.exchangeId}" class="btn btn-outline">Message</a>
                        <form action="${pageContext.request.contextPath}/student/completeExchange" method="post" style="display:inline;">
                            <input type="hidden" name="exchangeId" value="${exchange.exchangeId}">
                            <button type="submit" class="btn btn-primary" style="background: linear-gradient(135deg, #22c55e, #16a34a);">Mark Complete</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    
    </main>
</body>
</html>