<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Sign in to your SkillSwap account and continue your skill exchange journey.">
    <title>Login — SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="orb orb-1"></div>
<div class="orb orb-2"></div>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="nav-brand" id="nav-brand">⚡ SkillSwap</a>
    <div class="nav-links" id="nav-links">
        <a href="${pageContext.request.contextPath}/login" id="nav-login">Login</a>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-sm" id="nav-register" style="margin-left:8px;">Register</a>
        <a href="${pageContext.request.contextPath}/admin/login" class="btn btn-outline btn-sm" id="nav-admin" style="margin-left:8px;">Admin</a>
    </div>
</nav>

<main class="container animate-fade-in">

    <c:if test="${not empty error}">
        <div class="alert alert-error">⚠️ ${error}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">⚠️ ${param.error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">✅ ${message}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">✅ ${param.success}</div>
    </c:if>

    <div style="max-width:440px; margin:40px auto;">
        <div style="text-align:center;margin-bottom:32px;">
            <div style="font-size:2.5rem;margin-bottom:12px;">👋</div>
            <h2 style="font-size:1.9rem;">Welcome back</h2>
            <p style="margin-top:6px;">Sign in to continue your skill journey</p>
        </div>
        <div class="glass-panel">
            <form action="${pageContext.request.contextPath}/login" method="post" id="login-form">
                <div class="form-group">
                    <label for="login-email">Email Address</label>
                    <input type="email" id="login-email" name="email" class="form-control" required placeholder="you@example.com" autocomplete="email">
                </div>
                <div class="form-group" style="margin-bottom:28px;">
                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
                        <label for="login-password" style="margin-bottom:0;">Password</label>
                        <a href="${pageContext.request.contextPath}/forgot-password" id="forgot-password-link" style="color:var(--primary-light);font-size:0.85rem;text-decoration:none;font-weight:500;transition:opacity 0.2s;" onmouseover="this.style.textDecoration='underline'" onmouseout="this.style.textDecoration='none'">Forgot Password?</a>
                    </div>
                    <input type="password" id="login-password" name="password" class="form-control" required placeholder="••••••••" autocomplete="current-password">
                </div>
                <button type="submit" class="btn btn-primary w-full" id="login-submit" style="width:100%;padding:14px;">Sign In →</button>
            </form>
        </div>
        <p class="text-center mt-4" style="color:var(--text-muted);font-size:0.9rem;">
            Don't have an account? <a href="${pageContext.request.contextPath}/register" id="go-register" style="color:var(--primary-light);font-weight:600;">Create one →</a>
        </p>
    </div>

</main>

</body>
</html>