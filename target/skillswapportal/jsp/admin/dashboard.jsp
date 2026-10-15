<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="nav-links">
            
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/students">Manage Students</a>
            <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline" style="padding: 6px 16px;">Logout</a>
        
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
            <h1>Admin Dashboard</h1>
        </div>
        <div class="grid grid-cols-3">
            <div class="glass-panel">
                <h3>Total Students</h3>
                <p style="font-size: 2.5rem; font-weight: 700; color: var(--primary);">${stats.totalStudents != null ? stats.totalStudents : '0'}</p>
            </div>
            <div class="glass-panel">
                <h3>Total Skills</h3>
                <p style="font-size: 2.5rem; font-weight: 700; color: var(--accent);">${stats.totalSkills != null ? stats.totalSkills : '0'}</p>
            </div>
            <div class="glass-panel">
                <h3>Active Exchanges</h3>
                <p style="font-size: 2.5rem; font-weight: 700; color: #4ade80;">${stats.activeExchanges != null ? stats.activeExchanges : '0'}</p>
            </div>
        </div>
    
    </main>
</body>
</html>\n