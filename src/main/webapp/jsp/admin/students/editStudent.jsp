<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Student - Admin SkillSwap</title>
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
        
        <div class="glass-panel" style="max-width: 600px; margin: 0 auto;">
            <h2>Edit Student</h2>
            <form action="${pageContext.request.contextPath}/admin/editStudent" method="post" class="mt-4">
                <input type="hidden" name="id" value="${student.userId}">
                
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="fullName" class="form-control" value="${student.fullName}" required>
                </div>
                
                <div class="form-group">
                    <label>College Name</label>
                    <input type="text" name="collegeName" class="form-control" value="${student.collegeName}">
                </div>
                
                <div class="form-group">
                    <label>Location</label>
                    <input type="text" name="location" class="form-control" value="${student.location}">
                </div>
                
                <div class="form-group">
                    <label>Status</label>
                    <select name="status" class="form-control">
                        <option value="ACTIVE" ${student.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                        <option value="BLOCKED" ${student.status == 'BLOCKED' ? 'selected' : ''}>BLOCKED</option>
                    </select>
                </div>
                
                <div class="flex gap-2" style="margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-outline" style="flex: 1; text-align: center;">Cancel</a>
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Save Changes</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
