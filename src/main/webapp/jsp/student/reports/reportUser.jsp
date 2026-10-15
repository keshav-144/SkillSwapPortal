<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report User - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/search">See Students</a>
            <a href="${pageContext.request.contextPath}/student/activeExchanges">Exchanges</a>
        </div>
    </nav>
    <main class="container animate-fade-in">
        <div class="glass-panel" style="max-width: 500px; margin: 0 auto; border-color: rgba(239, 68, 68, 0.3);">
            <h2 style="color: #ef4444;">🚩 Report User</h2>
            <p style="color: var(--text-muted); margin-bottom: 20px;">Please tell us why you are reporting this user. If they are asking for money for a skill swap, select "Demanding money".</p>
            
            <form action="${pageContext.request.contextPath}/student/submitReport" method="post">
                <input type="hidden" name="reportedUserId" value="${param.reportedUserId}">
                <input type="hidden" name="returnUrl" value="${param.returnUrl}">
                
                <div class="form-group">
                    <label>Reason</label>
                    <select name="reason" class="form-control" required>
                        <option value="">Select a reason...</option>
                        <option value="Demanding money">Demanding money (Rs)</option>
                        <option value="Abusive language">Abusive language or Harassment</option>
                        <option value="Spam or Fake Profile">Spam or Fake Profile</option>
                        <option value="Inappropriate Content">Inappropriate Content</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Additional Details (Optional)</label>
                    <textarea name="description" class="form-control" rows="4" placeholder="Describe what happened..."></textarea>
                </div>
                
                <div class="flex gap-2">
                    <button type="submit" class="btn btn-primary" style="background-color: #ef4444; border-color: #ef4444;">Submit Report</button>
                    <a href="${param.returnUrl}" class="btn btn-outline">Cancel</a>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
