<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Send Swap Request - SkillSwap</title>
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
        
        
        <div class="glass-panel" style="max-width: 600px; margin: 0 auto;">
            <h2>Send Swap Request</h2>
            <form action="${pageContext.request.contextPath}/student/sendSwapRequest" method="post" class="mt-4">
                <input type="hidden" name="receiverId" value="${param.receiverId}">
                <div class="form-group">
                    <label>Skill You Want to Learn</label>
                    <select name="learnSkillId" class="form-control" required>
                        <c:forEach var="skill" items="${receiverSkills}">
                            <option value="${skill.skillId}">${skill.skillName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Skill You Will Teach</label>
                    <select name="teachSkillId" class="form-control" required>
                        <c:forEach var="skill" items="${mySkills}">
                            <option value="${skill.skillId}">${skill.skillName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Message (Optional)</label>
                    <textarea name="message" class="form-control" rows="3" placeholder="Hi, I would love to swap skills with you!"></textarea>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%;">Send Request</button>
            </form>
        </div>
    
    </main>
</body>
</html>\n