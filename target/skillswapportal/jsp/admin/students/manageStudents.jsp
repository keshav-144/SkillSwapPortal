<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Students - Admin SkillSwap</title>
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
        <c:if test="${not empty param.error}">
            <div class="glass-panel" style="background: rgba(239, 68, 68, 0.1); border-color: rgba(239, 68, 68, 0.3); margin-bottom: 20px;">
                <p style="color: #f87171;">${param.error}</p>
            </div>
        </c:if>
        <c:if test="${not empty param.success}">
            <div class="glass-panel" style="background: rgba(34, 197, 94, 0.1); border-color: rgba(34, 197, 94, 0.3); margin-bottom: 20px;">
                <p style="color: #4ade80;">${param.success}</p>
            </div>
        </c:if>
        
        <div class="flex justify-between items-center mb-4">
            <h1>Manage Students</h1>
        </div>
        
        <div class="glass-panel">
            <table class="data-table" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr style="border-bottom: 1px solid rgba(255,255,255,0.1); text-align: left;">
                        <th style="padding: 10px;">ID</th>
                        <th style="padding: 10px;">Name</th>
                        <th style="padding: 10px;">Language Teach</th>
                        <th style="padding: 10px;">Language Learn</th>
                        <th style="padding: 10px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr style="border-bottom: 1px solid rgba(255,255,255,0.05);">
                            <td style="padding: 10px;">${student.userId}</td>
                            <td style="padding: 10px; font-weight: bold;">${student.fullName}</td>
                            <td style="padding: 10px;">
                                <c:choose>
                                    <c:when test="${not empty student.taughtSkills}">
                                        <c:forEach var="ts" items="${student.taughtSkills}">
                                            <span class="skill-tag" style="margin: 2px;">${ts}</span>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #666; font-size: 0.8rem;">None</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="padding: 10px;">
                                <c:choose>
                                    <c:when test="${not empty student.wantedSkills}">
                                        <c:forEach var="ws" items="${student.wantedSkills}">
                                            <span class="skill-tag wanted-tag" style="margin: 2px;">${ws}</span>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #666; font-size: 0.8rem;">None</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="padding: 10px;">
                                <a href="${pageContext.request.contextPath}/admin/editStudent?id=${student.userId}" class="btn btn-outline" style="padding: 4px 8px; font-size: 0.8rem; margin-right: 5px;">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/deleteStudent?id=${student.userId}" class="btn btn-outline" style="padding: 4px 8px; font-size: 0.8rem; border-color: #ef4444; color: #ef4444;" onclick="return confirm('Are you sure you want to delete this student completely?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty students}">
                        <tr>
                            <td colspan="5" style="padding: 20px; text-align: center; color: var(--text-muted);">No students found.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
