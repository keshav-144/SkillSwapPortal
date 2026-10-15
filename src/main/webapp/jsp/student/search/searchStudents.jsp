<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>See Students - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function toggleMenu() {
            document.getElementById('nav-links').classList.toggle('active');
        }
    </script>
    <style>
        .student-card {
            background: rgba(255, 255, 255, 0.03);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            padding: 20px;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .student-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            border-color: var(--primary);
        }
        .skill-tag {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 15px;
            font-size: 0.8rem;
            margin: 3px;
            background: rgba(99, 102, 241, 0.1);
            color: var(--primary);
            border: 1px solid rgba(99, 102, 241, 0.2);
        }
        .wanted-tag {
            background: rgba(236, 72, 153, 0.1);
            color: #ec4899;
            border: 1px solid rgba(236, 72, 153, 0.2);
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="menu-toggle" onclick="toggleMenu()">
            <span></span><span></span><span></span>
        </div>
        <div class="nav-links" id="nav-links">
            <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/search">See Students</a>
            <a href="${pageContext.request.contextPath}/student/mySkills">My Skills</a>
            <a href="${pageContext.request.contextPath}/student/receivedRequests">Requests</a>
            <a href="${pageContext.request.contextPath}/student/activeExchanges">Exchanges</a>
            <a href="${pageContext.request.contextPath}/student/profile">Profile</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline" style="padding: 6px 16px;">Logout</a>
        </div>
    </nav>
    <main class="container animate-fade-in">
        <div class="flex justify-between items-center mb-4">
            <h1>See Students</h1>
        </div>
        
        <div class="glass-panel mb-4">
            <form action="${pageContext.request.contextPath}/student/search" method="get" class="flex gap-4">
                <input type="text" name="query" class="form-control" placeholder="Search by skill or name..." value="${query}" style="flex: 2;">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <div class="grid grid-cols-3">
            <c:forEach var="result" items="${searchResults}">
                <div class="student-card">
                    <h3 style="color: var(--text-main); margin-bottom: 5px;">${result.fullName}</h3>
                    <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 15px;">${result.collegeName}</p>
                    
                    <c:set var="pronounTeach" value="Skills They Teach:" />
                    <c:set var="pronounWant" value="Skills They Want:" />
                    <c:if test="${result.gender == 'Male'}">
                        <c:set var="pronounTeach" value="Skills He Teaches:" />
                        <c:set var="pronounWant" value="Skills He Wants:" />
                    </c:if>
                    <c:if test="${result.gender == 'Female'}">
                        <c:set var="pronounTeach" value="Skills She Teaches:" />
                        <c:set var="pronounWant" value="Skills She Wants:" />
                    </c:if>
                    
                    <div style="margin-bottom: 15px;">
                        <strong style="font-size: 0.85rem; color: var(--text-muted); display: block; margin-bottom: 5px;">${pronounTeach}</strong>
                        <c:choose>
                            <c:when test="${not empty result.taughtSkills}">
                                <c:forEach var="ts" items="${result.taughtSkills}">
                                    <span class="skill-tag">${ts}</span>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span style="font-size: 0.8rem; color: #666;">No skills listed</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <div style="margin-bottom: 20px;">
                        <strong style="font-size: 0.85rem; color: var(--text-muted); display: block; margin-bottom: 5px;">${pronounWant}</strong>
                        <c:choose>
                            <c:when test="${not empty result.wantedSkills}">
                                <c:forEach var="ws" items="${result.wantedSkills}">
                                    <span class="skill-tag wanted-tag">${ws}</span>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span style="font-size: 0.8rem; color: #666;">No skills listed</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <c:set var="reqStatus" value="NONE" />
                    <c:set var="reqId" value="" />
                    <c:forEach var="sentReq" items="${sentRequests}">
                        <c:if test="${sentReq.receiverId == result.userId && sentReq.status == 'PENDING'}">
                            <c:set var="reqStatus" value="SENT" />
                        </c:if>
                        <c:if test="${sentReq.receiverId == result.userId && sentReq.status == 'ACCEPTED'}">
                            <c:set var="reqStatus" value="ACCEPTED" />
                        </c:if>
                    </c:forEach>
                    <c:forEach var="recReq" items="${receivedRequests}">
                        <c:if test="${recReq.senderId == result.userId && recReq.status == 'PENDING'}">
                            <c:set var="reqStatus" value="RECEIVED" />
                            <c:set var="reqId" value="${recReq.requestId}" />
                        </c:if>
                        <c:if test="${recReq.senderId == result.userId && recReq.status == 'ACCEPTED'}">
                            <c:set var="reqStatus" value="ACCEPTED" />
                        </c:if>
                    </c:forEach>

                    <div class="flex gap-2" style="margin-top: 15px;">
                        <a href="${pageContext.request.contextPath}/student/viewProfile?userId=${result.userId}" class="btn btn-outline" style="flex: 1; text-align: center; padding: 6px;">Profile</a>
                        
                        <c:choose>
                            <c:when test="${reqStatus == 'SENT'}">
                                <button class="btn btn-outline" style="flex: 1.5; cursor: not-allowed; opacity: 0.7; padding: 6px;" disabled>Pending</button>
                            </c:when>
                            <c:when test="${reqStatus == 'RECEIVED'}">
                                <a href="${pageContext.request.contextPath}/student/respondRequest?requestId=${reqId}&action=accept" class="btn btn-primary" style="flex: 1; text-align: center; background: #22c55e; border-color: #22c55e; padding: 6px;">Agree</a>
                                <a href="${pageContext.request.contextPath}/student/respondRequest?requestId=${reqId}&action=reject" class="btn btn-outline" style="flex: 1; text-align: center; border-color: #ef4444; color: #ef4444; padding: 6px;">Disagree</a>
                            </c:when>
                            <c:when test="${reqStatus == 'ACCEPTED'}">
                                <button class="btn btn-outline" style="flex: 1.5; border-color: #3b82f6; color: #3b82f6; padding: 6px;" disabled>Connected</button>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/student/prepareRequest?receiverId=${result.userId}" class="btn btn-primary" style="flex: 1.5; text-align: center; padding: 6px;">Send Request</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
            
            <c:if test="${empty searchResults}">
                <div style="grid-column: 1 / -1;" class="text-center mt-4">
                    <p style="color: var(--text-muted);">No students found. Try different terms or browse all.</p>
                </div>
            </c:if>
        </div>
        
        <c:if test="${totalPages > 1}">
            <div class="pagination flex justify-center gap-2 mt-4">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/student/search?query=${param.query}&page=${i}" 
                       class="btn ${currentPage == i ? 'btn-primary' : 'btn-outline'}" 
                       style="padding: 6px 12px; font-size: 0.9rem;">${i}</a>
                </c:forEach>
            </div>
        </c:if>
    </main>
</body>
</html>