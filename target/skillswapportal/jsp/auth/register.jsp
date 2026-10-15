<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="nav-links">
            
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary" style="padding: 6px 16px;">Register</a>
            <a href="${pageContext.request.contextPath}/admin/login" class="btn btn-outline" style="padding: 6px 16px; margin-left: 10px;">Admin</a>
        
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
        
        
        <div class="glass-panel" style="max-width: 700px; margin: 40px auto;">
            <h2 class="text-center">Create an Account</h2>
            <p class="text-center mb-4" style="color: var(--text-muted);">Join the skill sharing community</p>
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="grid grid-cols-2">
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="fullName" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Phone Number</label>
                        <input type="text" name="phone" class="form-control">
                    </div>
                </div>
                <div class="grid grid-cols-2">
                    <div class="form-group">
                        <label>College Name</label>
                        <input type="text" name="collegeName" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Course</label>
                        <input type="text" name="course" class="form-control">
                    </div>
                </div>
                <div class="grid grid-cols-2">
                    <div class="form-group">
                        <label>Year</label>
                        <input type="number" name="year" class="form-control" min="1" max="10">
                    </div>
                    <div class="form-group">
                        <label>Gender</label>
                        <select name="gender" class="form-control">
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 10px;">Register</button>
            </form>
        </div>
    
    </main>
</body>
</html>\n