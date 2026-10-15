<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Messages - SkillSwap</title>
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
            <h1>Messages</h1>
        </div>
        <div class="grid grid-cols-3" style="min-height: 500px;">
            <div class="glass-panel" style="grid-column: span 1; overflow-y: auto;">
                <h3>Conversations</h3>
                <div class="mt-4">
                    <c:forEach var="conv" items="${conversations}">
                        <div style="padding: 12px; background: rgba(255,255,255,0.05); border-radius: 8px; margin-bottom: 8px; cursor: pointer;"
                             onclick="window.location.href='${pageContext.request.contextPath}/student/messages?exchangeId=${conv.exchangeId}'">
                            <strong>${conv.otherStudentName}</strong>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="glass-panel flex flex-col" style="grid-column: span 2; display: flex; flex-direction: column;">
                <c:if test="${not empty currentExchangeId}">
                    <div style="flex: 1; overflow-y: auto; margin-bottom: 20px;">
                        <c:forEach var="msg" items="${messages}">
                            <div style="margin-bottom: 16px; text-align: ${msg.senderId == sessionUser.id ? 'right' : 'left'};">
                                <div style="display: inline-block; padding: 10px 16px; border-radius: 16px;
                                            background: ${msg.senderId == sessionUser.id ? 'var(--primary)' : 'rgba(255,255,255,0.1)'};
                                            color: ${msg.senderId == sessionUser.id ? 'white' : 'var(--text-main)'};">
                                    ${msg.content}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Send Message Stub -->
                </c:if>
            </div>
        </div>
    
    </main>
</body>
</html>\n