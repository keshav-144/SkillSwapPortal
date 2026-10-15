<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications — SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .nav-links.active { display: flex !important; }

        /* ── Notification Card ── */
        .notif-card {
            display: flex;
            align-items: flex-start;
            gap: 16px;
            padding: 18px 20px;
            border-radius: 14px;
            border: 1px solid var(--border);
            background: var(--bg-card);
            backdrop-filter: blur(16px);
            transition: all 0.25s ease;
            position: relative;
            overflow: hidden;
        }
        .notif-card:hover {
            border-color: rgba(108,99,255,0.35);
            transform: translateX(4px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }
        /* Unread indicator stripe */
        .notif-card.unread::before {
            content: '';
            position: absolute;
            left: 0; top: 0; bottom: 0;
            width: 3px;
            background: linear-gradient(to bottom, var(--primary), var(--accent));
            border-radius: 3px 0 0 3px;
        }
        .notif-card.unread {
            background: linear-gradient(135deg, rgba(108,99,255,0.08), var(--bg-card));
            border-color: rgba(108,99,255,0.2);
        }

        /* ── Notification Icon ── */
        .notif-icon {
            width: 44px; height: 44px;
            border-radius: 12px;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.3rem;
            flex-shrink: 0;
        }
        .notif-icon.swap_request { background: rgba(108,99,255,0.15); }
        .notif-icon.swap_accept  { background: rgba(6,214,160,0.15); }
        .notif-icon.swap_reject  { background: rgba(247,37,133,0.15); }
        .notif-icon.system       { background: rgba(76,201,240,0.15); }
        .notif-icon.default      { background: rgba(255,214,10,0.12); }

        /* ── Notif body ── */
        .notif-body { flex: 1; min-width: 0; }
        .notif-title {
            font-weight: 600;
            font-size: 0.95rem;
            color: var(--text);
            margin-bottom: 4px;
        }
        .notif-message {
            font-size: 0.88rem;
            color: var(--text-muted);
            line-height: 1.55;
            margin-bottom: 8px;
        }
        .notif-time {
            font-size: 0.72rem;
            color: var(--text-faint);
            display: flex;
            align-items: center;
            gap: 5px;
        }

        /* ── Unread dot ── */
        .unread-dot {
            width: 8px; height: 8px;
            border-radius: 50%;
            background: var(--primary);
            flex-shrink: 0;
            margin-top: 6px;
            box-shadow: 0 0 6px rgba(108,99,255,0.6);
        }
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

    <%-- Flash messages --%>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">⚠️ ${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">✅ ${param.success}</div>
    </c:if>

    <%-- Page Header --%>
    <div class="page-header">
        <div class="page-header-left">
            <h1>🔔 Notifications</h1>
            <p>Your latest updates and activity alerts</p>
        </div>
    </div>

    <%-- Notifications List --%>
    <c:choose>
        <c:when test="${not empty notifications}">
            <div class="flex flex-col gap-3">
                <c:forEach var="notif" items="${notifications}" varStatus="status">

                    <%-- Determine icon based on type --%>
                    <c:set var="notifIcon" value="🔔"/>
                    <c:set var="notifClass" value="default"/>
                    <c:choose>
                        <c:when test="${notif.notificationType == 'SWAP_REQUEST'}">
                            <c:set var="notifIcon" value="📨"/>
                            <c:set var="notifClass" value="swap_request"/>
                        </c:when>
                        <c:when test="${notif.notificationType == 'SWAP_ACCEPT'}">
                            <c:set var="notifIcon" value="✅"/>
                            <c:set var="notifClass" value="swap_accept"/>
                        </c:when>
                        <c:when test="${notif.notificationType == 'SWAP_REJECT'}">
                            <c:set var="notifIcon" value="❌"/>
                            <c:set var="notifClass" value="swap_reject"/>
                        </c:when>
                        <c:when test="${notif.notificationType == 'SYSTEM'}">
                            <c:set var="notifIcon" value="⚙️"/>
                            <c:set var="notifClass" value="system"/>
                        </c:when>
                    </c:choose>

                    <%--
                        FIX: In JSTL EL, boolean getter isRead() must be accessed
                        as ${notif.read} NOT ${notif.isRead}
                    --%>
                    <div class="notif-card ${notif.read ? '' : 'unread'}" id="notif-${notif.notificationId}">

                        <%-- Icon --%>
                        <div class="notif-icon ${notifClass}">${notifIcon}</div>

                        <%-- Body --%>
                        <div class="notif-body">
                            <div class="notif-title">${not empty notif.title ? notif.title : 'Notification'}</div>
                            <div class="notif-message">${notif.message}</div>
                            <div class="notif-time">
                                🕐 ${notif.createdAt}
                                <c:if test="${not empty notif.notificationType}">
                                    · <span class="badge badge-primary" style="font-size:0.68rem;padding:2px 8px;">${notif.notificationType}</span>
                                </c:if>
                            </div>
                        </div>

                        <%-- Unread dot (only for unread ones) --%>
                        <c:if test="${not notif.read}">
                            <div class="unread-dot" title="Unread"></div>
                        </c:if>

                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <%-- Empty State --%>
            <div class="glass-panel text-center" style="padding:60px 24px;">
                <div style="font-size:3.5rem;margin-bottom:16px;opacity:0.4;">🔕</div>
                <h3 style="font-size:1.1rem;font-weight:600;color:var(--text-muted);text-transform:none;letter-spacing:0;margin-bottom:8px;">
                    No notifications yet
                </h3>
                <p style="font-size:0.9rem;color:var(--text-faint);">
                    When you receive swap requests or updates, they'll appear here.
                </p>
                <a href="${pageContext.request.contextPath}/student/search" class="btn btn-outline btn-sm" id="find-students-btn" style="margin-top:20px;">
                    Find Students →
                </a>
            </div>
        </c:otherwise>
    </c:choose>

</main>

</body>
</html>