<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Skills - SkillSwap</title>
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
            <h1>My Skills</h1>
        </div>
        <div class="grid grid-cols-2">
            <div class="glass-panel">
                <h3>Skills I Can Teach</h3>
                <a href="${pageContext.request.contextPath}/student/prepareAddSkill" class="btn btn-primary mt-4 mb-4" style="display: inline-block;">+ Add New Skill</a>
                <ul style="list-style: none;">
                    <c:forEach var="mySkill" items="${mySkills}">
                        <li style="padding: 12px; background: rgba(255,255,255,0.05); margin-bottom: 8px; border-radius: 8px;">
                            <div class="flex justify-between items-center mb-2">
                                <span>${mySkill.skillName}</span>
                                <div class="flex gap-2">
                                    <a href="${pageContext.request.contextPath}/student/prepareEditSkill?skillId=${mySkill.skillId}" class="btn btn-outline" style="padding: 4px 8px; text-decoration: none;">Edit</a>
                                    <form action="${pageContext.request.contextPath}/student/deleteSkill" method="post" style="display:inline;">
                                        <input type="hidden" name="studentSkillId" value="${mySkill.studentSkillId}">
                                        <button type="submit" class="btn btn-outline" style="padding: 4px 8px; border-color: #ef4444; color: #ef4444;">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="glass-panel">
                <h3>Skills I Want to Learn</h3>
                <a href="${pageContext.request.contextPath}/student/wantedSkills" class="btn btn-outline mt-4">Manage Wanted Skills</a>
            </div>
        </div>
    
    </main>
</body>
</html>\n