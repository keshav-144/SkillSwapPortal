<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="nav-links">
            
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/search">Find Students</a>
            <a href="${pageContext.request.contextPath}/student/skills">My Skills</a>
            <a href="${pageContext.request.contextPath}/student/requests/received">Requests</a>
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
            <h1>My Profile</h1>
            <a href="${pageContext.request.contextPath}/student/editProfile" class="btn btn-primary">Edit Profile</a>
        </div>
        
        <div class="glass-panel grid grid-cols-2">
            <div>
                <h3 style="color: var(--primary);">${userProfile.fullName != null ? sessionUser.fullName : sessionUser.firstName}</h3>
                <p style="color: var(--text-muted); margin-bottom: 20px;">${userProfile.email}</p>
                <h4>Details</h4>
                <p style="margin-top: 10px;"><strong>College:</strong> ${userProfile.collegeName}</p>
                <p style="margin-top: 10px;"><strong>Course:</strong> ${userProfile.course} (Year ${userProfile.year})</p>
                
                <h4 class="mt-4">Bio</h4>
                <p style="margin-top: 10px; line-height: 1.6;">${not empty sessionUser.bio ? sessionUser.bio : 'No bio added yet.'}</p>
            </div>
        </div>
    
    </main>
</body>
</html>\n