<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Messages — SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .nav-links.active { display: flex !important; }

        /* ── Layout ── */
        .msg-layout {
            display: grid;
            grid-template-columns: 280px 1fr;
            gap: 20px;
            height: calc(100vh - 180px);
            min-height: 500px;
        }
        @media (max-width: 700px) {
            .msg-layout { grid-template-columns: 1fr; height: auto; }
            .conversations-panel { height: 200px; }
        }

        /* ── Conversations sidebar ── */
        .conversations-panel {
            background: var(--bg-card);
            backdrop-filter: blur(20px);
            border: 1px solid var(--border);
            border-radius: 20px;
            padding: 20px;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
            gap: 6px;
        }
        .conv-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 14px;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.2s;
            border: 1px solid transparent;
            text-decoration: none;
        }
        .conv-item:hover {
            background: rgba(108,99,255,0.1);
            border-color: rgba(108,99,255,0.2);
        }
        .conv-item.active {
            background: rgba(108,99,255,0.15);
            border-color: rgba(108,99,255,0.4);
        }
        .conv-avatar {
            width: 38px; height: 38px;
            border-radius: 50%;
            background: linear-gradient(135deg, var(--primary), var(--accent));
            display: flex; align-items: center; justify-content: center;
            font-size: 1rem; font-weight: 700; color: #fff;
            flex-shrink: 0;
        }
        .conv-name {
            font-size: 0.9rem;
            font-weight: 600;
            color: var(--text);
        }

        /* ── Chat panel ── */
        .chat-panel {
            background: var(--bg-card);
            backdrop-filter: blur(20px);
            border: 1px solid var(--border);
            border-radius: 20px;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }
        .chat-header {
            padding: 18px 24px;
            border-bottom: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
            flex-shrink: 0;
        }
        .chat-header-info {
            display: flex; align-items: center; gap: 12px;
        }
        .chat-header-name {
            font-weight: 600;
            font-size: 1rem;
            color: var(--text);
        }
        .chat-header-status {
            font-size: 0.75rem;
            color: var(--green);
            display: flex; align-items: center; gap: 4px;
        }
        .chat-header-status::before {
            content: '';
            width: 6px; height: 6px;
            background: var(--green);
            border-radius: 50%;
        }

        /* ── Messages area ── */
        .chat-messages {
            flex: 1;
            overflow-y: auto;
            padding: 20px 24px;
            display: flex;
            flex-direction: column;
            gap: 16px;
        }

        /* ── Individual message row ── */
        .msg-row {
            display: flex;
            align-items: flex-end;
            gap: 10px;
        }
        .msg-row.sent {
            flex-direction: row-reverse; /* sent: avatar on right */
        }

        /* Small avatar next to messages */
        .msg-avatar {
            width: 32px; height: 32px;
            border-radius: 50%;
            background: linear-gradient(135deg, var(--primary), var(--accent));
            display: flex; align-items: center; justify-content: center;
            font-size: 0.8rem; font-weight: 700; color: #fff;
            flex-shrink: 0;
            box-shadow: 0 2px 8px rgba(108,99,255,0.3);
            overflow: hidden;
        }
        .msg-avatar img {
            width: 100%; height: 100%;
            object-fit: cover;
            border-radius: 50%;
        }
        /* Sent messages get accent color avatar */
        .msg-row.sent .msg-avatar {
            background: linear-gradient(135deg, var(--accent), var(--primary));
        }

        .msg-content { max-width: 65%; display: flex; flex-direction: column; gap: 4px; }
        .msg-row.sent .msg-content { align-items: flex-end; }

        .msg-bubble {
            padding: 10px 16px;
            border-radius: 18px;
            font-size: 0.9rem;
            line-height: 1.55;
            word-break: break-word;
        }
        .msg-row.received .msg-bubble {
            background: rgba(255,255,255,0.06);
            border: 1px solid rgba(255,255,255,0.08);
            color: var(--text);
            border-bottom-left-radius: 4px;
        }
        .msg-row.sent .msg-bubble {
            background: linear-gradient(135deg, var(--primary), #5A52E5);
            color: #fff;
            border-bottom-right-radius: 4px;
            box-shadow: 0 2px 12px rgba(108,99,255,0.3);
        }
        .msg-time {
            font-size: 0.68rem;
            color: var(--text-faint);
            padding: 0 4px;
        }

        /* ── Message input ── */
        .chat-input-area {
            padding: 16px 20px;
            border-top: 1px solid var(--border);
            display: flex;
            gap: 10px;
            align-items: center;
            flex-shrink: 0;
            background: rgba(5,7,13,0.4);
        }
        .chat-input-area .form-control {
            flex: 1;
            border-radius: 24px;
            padding: 12px 20px;
        }
        .chat-input-area .btn {
            border-radius: 24px;
            padding: 12px 22px;
            flex-shrink: 0;
        }

        /* Empty state */
        .chat-empty {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: var(--text-faint);
            gap: 12px;
        }
        .chat-empty .empty-icon { font-size: 3rem; opacity: 0.4; }
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

<main class="container animate-fade-in" style="padding-top:28px;">

    <c:if test="${not empty error}">
        <div class="alert alert-error">⚠️ ${error}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">⚠️ ${param.error}</div>
    </c:if>

    <div class="flex justify-between items-center mb-4">
        <h1 style="font-size:1.8rem;margin-bottom:0;">💬 Messages</h1>
        <c:if test="${not empty partnerId}">
            <a href="${pageContext.request.contextPath}/student/reports/report?reportedUserId=${partnerId}"
               class="btn btn-sm" style="border:1px solid rgba(247,37,133,0.4);color:var(--accent-light);background:rgba(247,37,133,0.06);"
               id="report-user-btn">🚩 Report User</a>
        </c:if>
    </div>

    <div class="msg-layout">

        <%-- ── Conversations Sidebar ── --%>
        <div class="conversations-panel" id="conversations-panel">
            <div class="section-label" style="margin-bottom:8px;">Conversations</div>
            <c:choose>
                <c:when test="${not empty conversations}">
                    <c:forEach var="conv" items="${conversations}">
                        <a href="${pageContext.request.contextPath}/student/messages?exchangeId=${conv.exchangeId}"
                           class="conv-item ${conv.exchangeId == exchangeId ? 'active' : ''}" id="conv-${conv.exchangeId}">
                            <div class="conv-avatar">
                                ${not empty conv.partnerName ? conv.partnerName.substring(0,1).toUpperCase() : '?'}
                            </div>
                            <div class="conv-name">${conv.partnerName}</div>
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="text-sm text-muted" style="text-align:center;margin-top:20px;">No conversations yet</p>
                </c:otherwise>
            </c:choose>
        </div>

        <%-- ── Chat Panel ── --%>
        <div class="chat-panel" id="chat-panel">
            <c:choose>
                <c:when test="${not empty exchangeId}">

                    <%-- Chat Header --%>
                    <div class="chat-header">
                        <div class="chat-header-info">
                            <div class="msg-avatar" style="width:40px;height:40px;font-size:0.95rem;">
                                ${not empty partnerName ? partnerName.substring(0,1).toUpperCase() : '?'}
                            </div>
                            <div>
                                <div class="chat-header-name">${not empty partnerName ? partnerName : 'Partner'}</div>
                                <div class="chat-header-status">Active exchange</div>
                            </div>
                        </div>
                    </div>

                    <%-- Messages --%>
                    <div class="chat-messages" id="chat-messages">
                        <c:choose>
                            <c:when test="${empty chatHistory}">
                                <div class="chat-empty">
                                    <div class="empty-icon">💬</div>
                                    <p>No messages yet. Say hello!</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="msg" items="${chatHistory}">
                                    <%-- Determine if message is sent by logged-in user --%>
                                    <c:set var="isSent" value="${msg.senderId == loggedInUser.userId}"/>
                                    <div class="msg-row ${isSent ? 'sent' : 'received'}" id="msg-${msg.messageId}">

                                        <%-- Sender Avatar (small circle with first letter) --%>
                                        <div class="msg-avatar">
                                            <c:choose>
                                                <c:when test="${isSent}">
                                                    <%-- Own avatar --%>
                                                    <c:choose>
                                                        <c:when test="${not empty loggedInUser.profileImage}">
                                                            <img src="${pageContext.request.contextPath}/${loggedInUser.profileImage}" alt="You"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${not empty loggedInUser.fullName ? loggedInUser.fullName.substring(0,1).toUpperCase() : 'Y'}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <%-- Partner avatar: first letter of senderName --%>
                                                    <c:choose>
                                                        <c:when test="${not empty partnerProfileImage}">
                                                            <img src="${pageContext.request.contextPath}/${partnerProfileImage}" alt="${msg.senderName}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${not empty msg.senderName ? msg.senderName.substring(0,1).toUpperCase() : (not empty partnerName ? partnerName.substring(0,1).toUpperCase() : '?')}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="msg-content">
                                            <div class="msg-bubble">${msg.messageText}</div>
                                            <div class="msg-time">${msg.sentAt}</div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <%-- Message Input --%>
                    <div class="chat-input-area">
                        <form action="${pageContext.request.contextPath}/student/messages" method="post"
                              class="flex gap-2 w-full" id="msg-form">
                            <input type="hidden" name="exchangeId" value="${exchangeId}">
                            <input type="text" name="messageText" id="msg-input" class="form-control"
                                   placeholder="Type a message..." required autocomplete="off">
                            <button type="submit" class="btn btn-primary" id="msg-send-btn">Send ➤</button>
                        </form>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="chat-empty">
                        <div class="empty-icon">💬</div>
                        <p>Select a conversation to start chatting</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</main>

<script>
    // Auto-scroll to bottom of chat
    var chatMsgs = document.getElementById('chat-messages');
    if (chatMsgs) chatMsgs.scrollTop = chatMsgs.scrollHeight;
</script>

</body>
</html>