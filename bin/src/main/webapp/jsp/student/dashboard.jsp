<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard - SkillSwap</title>
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
        
        
        <h1>Welcome, ${loggedInUser.fullName != null ? sessionUser.fullName : sessionUser.firstName}!</h1>
        <div class="grid grid-cols-3 mt-4">
            <div class="glass-panel">
                <h3>My Skills</h3>
                <p style="font-size: 2rem; color: var(--primary);">${mySkillsCount != null ? mySkillsCount : '0'}</p>
                <a href="${pageContext.request.contextPath}/student/mySkills" class="btn btn-outline mt-4">Manage Skills</a>
            </div>
            <div class="glass-panel">
                <h3>Pending Requests</h3>
                <p style="font-size: 2rem; color: var(--accent);">${pendingRequestsCount != null ? pendingRequestsCount : '0'}</p>
                <a href="${pageContext.request.contextPath}/student/receivedRequests" class="btn btn-outline mt-4">View Requests</a>
            </div>
            <div class="glass-panel">
                <h3>Active Exchanges</h3>
                <p style="font-size: 2rem; color: #4ade80;">${activeExchangesCount != null ? activeExchangesCount : '0'}</p>
                <a href="${pageContext.request.contextPath}/student/activeExchanges" class="btn btn-outline mt-4">View Exchanges</a>
            </div>
        </div>
    
    </main>
</body>
</html>\n