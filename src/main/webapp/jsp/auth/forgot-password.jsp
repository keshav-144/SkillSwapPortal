<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Reset your SkillSwap account password securely.">
    <title>Forgot Password — SkillSwap</title>
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
            <div style="font-size:2.5rem;margin-bottom:12px;">🔐</div>
            <h2 style="font-size:1.9rem;">Reset Password</h2>
            <p style="margin-top:6px; color:var(--text-muted);">Enter your registered email and set a new password</p>
        </div>

        <div class="glass-panel">
            <form action="${pageContext.request.contextPath}/forgot-password" method="post" id="forgot-password-form" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="reset-email">Registered Email Address</label>
                    <input type="email" id="reset-email" name="email" class="form-control" required placeholder="you@example.com" autocomplete="email" value="${email}">
                </div>

                <div class="form-group" style="position:relative;">
                    <label for="reset-new-password">New Password</label>
                    <div style="position:relative;">
                        <input type="password" id="reset-new-password" name="newPassword" class="form-control" required minlength="6" placeholder="At least 6 characters" autocomplete="new-password" oninput="checkMatch()" style="padding-right:45px;">
                        <button type="button" onclick="togglePasswordVisibility('reset-new-password', this)" style="position:absolute; right:12px; top:50%; transform:translateY(-50%); background:none; border:none; cursor:pointer; color:var(--text-muted); font-size:1.1rem; padding:4px;" title="Toggle visibility">👁️</button>
                    </div>
                </div>

                <div class="form-group" style="position:relative; margin-bottom:20px;">
                    <label for="reset-confirm-password">Confirm New Password</label>
                    <div style="position:relative;">
                        <input type="password" id="reset-confirm-password" name="confirmPassword" class="form-control" required minlength="6" placeholder="Re-type new password" autocomplete="new-password" oninput="checkMatch()" style="padding-right:45px;">
                        <button type="button" onclick="togglePasswordVisibility('reset-confirm-password', this)" style="position:absolute; right:12px; top:50%; transform:translateY(-50%); background:none; border:none; cursor:pointer; color:var(--text-muted); font-size:1.1rem; padding:4px;" title="Toggle visibility">👁️</button>
                    </div>
                    <div id="password-match-hint" style="font-size:0.8rem; margin-top:6px; min-height:18px;"></div>
                </div>

                <button type="submit" class="btn btn-primary w-full" id="reset-submit" style="width:100%;padding:14px;font-size:1rem;font-weight:600;">Update Password 🔑</button>
            </form>
        </div>

        <p class="text-center mt-4" style="color:var(--text-muted);font-size:0.9rem;">
            Remember your password? <a href="${pageContext.request.contextPath}/login" id="back-to-login" style="color:var(--primary-light);font-weight:600;">Back to Login →</a>
        </p>
    </div>

</main>

<script>
function togglePasswordVisibility(fieldId, btn) {
    const field = document.getElementById(fieldId);
    if (field.type === 'password') {
        field.type = 'text';
        btn.textContent = '🙈';
    } else {
        field.type = 'password';
        btn.textContent = '👁️';
    }
}

function checkMatch() {
    const p1 = document.getElementById('reset-new-password').value;
    const p2 = document.getElementById('reset-confirm-password').value;
    const hint = document.getElementById('password-match-hint');

    if (!p2) {
        hint.textContent = '';
        return;
    }

    if (p1 === p2) {
        hint.style.color = 'var(--green, #06D6A0)';
        hint.textContent = '✓ Passwords match';
    } else {
        hint.style.color = 'var(--accent, #F72585)';
        hint.textContent = '✕ Passwords do not match';
    }
}

function validateForm() {
    const p1 = document.getElementById('reset-new-password').value;
    const p2 = document.getElementById('reset-confirm-password').value;
    if (p1.length < 6) {
        alert('Password must be at least 6 characters long.');
        return false;
    }
    if (p1 !== p2) {
        alert('Passwords do not match. Please ensure both passwords match.');
        return false;
    }
    return true;
}
</script>

</body>
</html>
