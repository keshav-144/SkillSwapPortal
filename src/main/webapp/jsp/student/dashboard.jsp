<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard — SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* ── Navbar toggle ── */
        .nav-links.active { display: flex !important; }

        /* ── Welcome Banner ── */
        .dash-welcome {
            display: flex;
            align-items: center;
            gap: 22px;
            margin-bottom: 32px;
            padding: 30px 36px;
            background: linear-gradient(135deg,
                rgba(108,99,255,0.12) 0%,
                rgba(12,16,28,0.75) 60%,
                rgba(247,37,133,0.08) 100%);
            backdrop-filter: blur(20px);
            border: 1px solid rgba(108,99,255,0.22);
            border-radius: 24px;
            position: relative;
            overflow: hidden;
        }
        .dash-welcome::before {
            content: '';
            position: absolute;
            top: 0; left: 0; right: 0;
            height: 3px;
            background: linear-gradient(90deg, var(--primary), var(--accent), var(--teal));
        }
        .dash-avatar {
            width: 64px; height: 64px;
            border-radius: 50%;
            background: linear-gradient(135deg, var(--primary), var(--accent));
            display: flex; align-items: center; justify-content: center;
            font-size: 1.6rem; font-weight: 700;
            color: #fff;
            flex-shrink: 0;
            box-shadow: 0 4px 20px rgba(108,99,255,0.4);
            border: 2px solid rgba(255,255,255,0.12);
        }
        .dash-welcome-name {
            font-family: 'Space Grotesk', sans-serif;
            font-size: 2rem;
            font-weight: 700;
            letter-spacing: -0.5px;
            color: #fff;
            margin-bottom: 6px;
        }
        .dash-welcome-sub {
            font-size: 0.9rem;
            color: var(--text-muted);
        }

        /* ── Stat Cards (2 cols) ── */
        .stats-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 32px;
        }
        @media (max-width: 600px) {
            .stats-row { grid-template-columns: 1fr; }
            .dash-welcome { padding: 22px 20px; }
            .dash-welcome-name { font-size: 1.5rem; }
        }
        .stat-box {
            background: var(--bg-card);
            backdrop-filter: blur(20px);
            border: 1px solid var(--border);
            border-radius: 20px;
            padding: 32px 28px;
            display: flex;
            flex-direction: column;
            gap: 10px;
            transition: all 0.3s cubic-bezier(0.4,0,0.2,1);
            position: relative;
            overflow: hidden;
        }
        .stat-box::after {
            content: '';
            position: absolute;
            inset: 0;
            background: radial-gradient(circle at top right, rgba(108,99,255,0.06), transparent 60%);
            pointer-events: none;
        }
        .stat-box:hover {
            transform: translateY(-5px);
            box-shadow: 0 16px 48px rgba(0,0,0,0.4);
        }
        .stat-box.pending:hover  { border-color: rgba(247,37,133,0.4); box-shadow: 0 16px 48px rgba(247,37,133,0.15); }
        .stat-box.exchange:hover { border-color: rgba(6,214,160,0.4);  box-shadow: 0 16px 48px rgba(6,214,160,0.15); }

        .stat-box .stat-icon {
            font-size: 2rem;
            margin-bottom: 4px;
        }
        .stat-box .stat-label {
            font-size: 0.72rem;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 1.2px;
            color: var(--text-muted);
        }
        .stat-box .stat-num {
            font-family: 'Space Grotesk', sans-serif;
            font-size: 4rem;
            font-weight: 800;
            letter-spacing: -3px;
            line-height: 1;
        }
        .stat-box .stat-btn {
            margin-top: 12px;
            width: fit-content;
        }

        /* ── Quick Links ── */
        .quick-links {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(170px, 1fr));
            gap: 12px;
        }
        .ql-card {
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: 14px;
            padding: 16px 18px;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 12px;
            color: var(--text-muted);
            font-size: 0.88rem;
            font-weight: 500;
            transition: all 0.25s ease;
        }
        .ql-card:hover {
            border-color: rgba(108,99,255,0.4);
            color: var(--text);
            background: rgba(108,99,255,0.08);
            transform: translateY(-2px);
        }
        .ql-card .ql-icon { font-size: 1.3rem; }
    </style>
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="nav-brand" id="nav-brand">⚡ SkillSwap</a>
    <div class="menu-toggle" onclick="document.getElementById('nav-links').classList.toggle('active')" id="menu-toggle">
        <span></span><span></span><span></span>
    </div>
    <div class="nav-links" id="nav-links">
        <a href="${pageContext.request.contextPath}/student/dashboard" id="nav-dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/search" id="nav-search">Find Students</a>
        <a href="${pageContext.request.contextPath}/student/mySkills" id="nav-skills">My Skills</a>
        <a href="${pageContext.request.contextPath}/student/receivedRequests" id="nav-requests">Requests</a>
        <a href="${pageContext.request.contextPath}/student/activeExchanges" id="nav-exchanges">Exchanges</a>
        <a href="${pageContext.request.contextPath}/student/profile" id="nav-profile">Profile</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline btn-sm" id="nav-logout" style="margin-left:8px;">Logout</a>
    </div>
</nav>

<main class="container animate-fade-in">

    <%-- Flash Messages --%>
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

    <%-- Welcome Banner --%>
    <div class="dash-welcome">
        <div class="dash-avatar" id="dash-avatar">
            <c:choose>
                <c:when test="${not empty loggedInUser.fullName}">${loggedInUser.fullName.substring(0,1).toUpperCase()}</c:when>
                <c:otherwise>?</c:otherwise>
            </c:choose>
        </div>
        <div>
            <div class="dash-welcome-name" id="dash-welcome-name">
                Welcome, <c:choose>
                    <c:when test="${not empty loggedInUser.fullName}">${loggedInUser.fullName}</c:when>
                    <c:otherwise>${loggedInUser.email}</c:otherwise>
                </c:choose>! 👋
            </div>
            <div class="dash-welcome-sub" id="dash-welcome-sub">
                <c:if test="${not empty loggedInUser.collegeName}">${loggedInUser.collegeName}</c:if>
                <c:if test="${not empty loggedInUser.collegeName and not empty loggedInUser.course}"> · </c:if>
                <c:if test="${not empty loggedInUser.course}">${loggedInUser.course}</c:if>
                <c:if test="${empty loggedInUser.collegeName and empty loggedInUser.course}">SkillSwap Member</c:if>
            </div>
        </div>
    </div>

    <%-- 2 Stat Cards — Pending Requests + Active Exchanges --%>
    <div class="stats-row">

        <div class="stat-box pending">
            <div class="stat-icon">📬</div>
            <div class="stat-label">Pending Requests</div>
            <div class="stat-num" id="pending-count" style="color:var(--accent-light);" data-target="${pendingRequestsCount != null ? pendingRequestsCount : '0'}">0</div>
            <a href="${pageContext.request.contextPath}/student/receivedRequests" class="btn btn-outline btn-sm stat-btn" id="dash-view-requests">View Requests →</a>
        </div>

        <div class="stat-box exchange">
            <div class="stat-icon">🔁</div>
            <div class="stat-label">Active Exchanges</div>
            <div class="stat-num" id="exchange-count" style="color:var(--green);" data-target="${activeExchangesCount != null ? activeExchangesCount : '0'}">0</div>
            <a href="${pageContext.request.contextPath}/student/activeExchanges" class="btn btn-outline btn-sm stat-btn" id="dash-view-exchanges">View Exchanges →</a>
        </div>

    </div>

    <%-- Quick Links --%>
    <div class="section-label">Quick Actions</div>
    <div class="quick-links">
        <a href="${pageContext.request.contextPath}/student/search" class="ql-card" id="ql-search">
            <span class="ql-icon">🔍</span> Find Students
        </a>
        <a href="${pageContext.request.contextPath}/student/mySkills" class="ql-card" id="ql-skills">
            <span class="ql-icon">✨</span> My Skills
        </a>
        <a href="${pageContext.request.contextPath}/student/notifications" class="ql-card" id="ql-notifs">
            <span class="ql-icon">🔔</span> Notifications
        </a>
        <a href="${pageContext.request.contextPath}/student/profile" class="ql-card" id="ql-profile">
            <span class="ql-icon">👤</span> My Profile
        </a>
    </div>

</main>

<%-- Count-Up Animation Script --%>
<script>
    function countUp(el) {
        var target = parseInt(el.getAttribute('data-target')) || 0;
        if (target === 0) return; // already 0, no animation needed
        var duration = 1200; // ms
        var stepTime = Math.max(20, Math.floor(duration / target));
        var current = 0;
        var timer = setInterval(function () {
            current += 1;
            el.textContent = current;
            if (current >= target) {
                el.textContent = target;
                clearInterval(timer);
            }
        }, stepTime);
    }

    document.addEventListener('DOMContentLoaded', function () {
        countUp(document.getElementById('pending-count'));
        countUp(document.getElementById('exchange-count'));
    });
</script>

</body>
</html>