<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile — SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .nav-links.active { display: flex !important; }

        /* ── Profile Header Card ── */
        .profile-header {
            display: flex;
            align-items: center;
            gap: 28px;
            padding: 32px 36px;
            background: linear-gradient(135deg, rgba(108,99,255,0.1), rgba(12,16,28,0.8), rgba(247,37,133,0.06));
            border: 1px solid rgba(108,99,255,0.22);
            border-radius: 24px;
            margin-bottom: 28px;
            position: relative;
            overflow: hidden;
        }
        .profile-header::before {
            content: '';
            position: absolute;
            top: 0; left: 0; right: 0;
            height: 3px;
            background: linear-gradient(90deg, var(--primary), var(--accent), var(--teal));
        }

        /* ── Avatar with upload overlay ── */
        .avatar-wrapper {
            position: relative;
            width: 96px; height: 96px;
            flex-shrink: 0;
        }
        .avatar-wrapper img,
        .avatar-wrapper .avatar-initials {
            width: 96px; height: 96px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid rgba(255,255,255,0.12);
            box-shadow: 0 4px 24px rgba(108,99,255,0.4);
            display: block;
        }
        .avatar-wrapper .avatar-initials {
            background: linear-gradient(135deg, var(--primary), var(--accent));
            display: flex; align-items: center; justify-content: center;
            font-size: 2.2rem; font-weight: 700; color: #fff;
            font-family: 'Space Grotesk', sans-serif;
        }
        /* Camera overlay button */
        .avatar-upload-btn {
            position: absolute;
            bottom: 0; right: 0;
            width: 30px; height: 30px;
            border-radius: 50%;
            background: var(--primary);
            border: 2px solid var(--bg);
            display: flex; align-items: center; justify-content: center;
            cursor: pointer;
            font-size: 0.85rem;
            transition: background 0.2s, transform 0.2s;
            box-shadow: 0 2px 8px rgba(108,99,255,0.5);
        }
        .avatar-upload-btn:hover {
            background: var(--accent);
            transform: scale(1.1);
        }
        /* Hidden file input */
        #profileImageInput { display: none; }

        /* ── Info section ── */
        .profile-info-name {
            font-family: 'Space Grotesk', sans-serif;
            font-size: 1.8rem;
            font-weight: 700;
            color: var(--text);
            margin-bottom: 6px;
        }
        .profile-info-meta {
            font-size: 0.88rem;
            color: var(--text-muted);
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-bottom: 14px;
        }
        .profile-info-meta span {
            display: flex; align-items: center; gap: 5px;
        }

        /* ── Upload progress toast ── */
        #upload-toast {
            display: none;
            position: fixed;
            bottom: 28px; right: 28px;
            background: rgba(12,16,28,0.95);
            border: 1px solid var(--primary);
            border-radius: 14px;
            padding: 14px 22px;
            color: var(--primary-light);
            font-size: 0.9rem;
            font-weight: 500;
            z-index: 999;
            box-shadow: var(--shadow-lg);
            animation: slideUp 0.3s ease;
        }
        @keyframes slideUp {
            from { opacity:0; transform: translateY(10px); }
            to   { opacity:1; transform: translateY(0); }
        }

        /* ── Detail Cards ── */
        .detail-row {
            display: flex;
            align-items: baseline;
            gap: 10px;
            padding: 12px 0;
            border-bottom: 1px solid rgba(108,99,255,0.08);
            font-size: 0.9rem;
        }
        .detail-row:last-child { border-bottom: none; }
        .detail-label {
            font-size: 0.72rem;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.8px;
            color: var(--text-faint);
            min-width: 80px;
        }
        .detail-value { color: var(--text); }

        @media (max-width: 640px) {
            .profile-header { flex-direction: column; align-items: flex-start; }
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

    <%-- Profile Header --%>
    <div class="profile-header">
        <%-- Avatar with camera upload button --%>
        <form id="img-upload-form" action="${pageContext.request.contextPath}/student/uploadProfileImage"
              method="post" enctype="multipart/form-data">
            <div class="avatar-wrapper">
                <c:choose>
                    <c:when test="${not empty userProfile.profileImage}">
                        <img src="${pageContext.request.contextPath}/${userProfile.profileImage}"
                             alt="${userProfile.fullName}" id="avatar-preview"/>
                    </c:when>
                    <c:otherwise>
                        <div class="avatar-initials" id="avatar-initials">
                            ${not empty userProfile.fullName ? userProfile.fullName.substring(0,1).toUpperCase() : '?'}
                        </div>
                    </c:otherwise>
                </c:choose>
                <%-- Camera icon — click triggers file input --%>
                <div class="avatar-upload-btn" onclick="document.getElementById('profileImageInput').click()" title="Change photo">📷</div>
            </div>
            <input type="file" id="profileImageInput" name="profileImage" accept="image/*" onchange="previewAndUpload(this)">
        </form>

        <%-- Name + Info --%>
        <div style="flex:1;">
            <div class="profile-info-name" id="profile-name">
                ${not empty userProfile.fullName ? userProfile.fullName : userProfile.email}
            </div>
            <div class="profile-info-meta">
                <c:if test="${not empty userProfile.collegeName}">
                    <span>🏛️ ${userProfile.collegeName}</span>
                </c:if>
                <c:if test="${not empty userProfile.course}">
                    <span>📚 ${userProfile.course}</span>
                    <c:if test="${userProfile.year > 0}"><span>Year ${userProfile.year}</span></c:if>
                </c:if>
                <c:if test="${not empty userProfile.location}">
                    <span>📍 ${userProfile.location}</span>
                </c:if>
                <c:if test="${not empty userProfile.gender}">
                    <span>👤 ${userProfile.gender}</span>
                </c:if>
            </div>
            <a href="${pageContext.request.contextPath}/student/editProfile" class="btn btn-primary btn-sm" id="edit-profile-btn">✏️ Edit Profile</a>
        </div>
    </div>

    <div class="grid grid-2" style="align-items:start;">

        <%-- Left: Details + Bio --%>
        <div class="flex flex-col gap-4">
            <div class="glass-panel">
                <div class="section-label">Contact & Details</div>
                <div class="detail-row">
                    <span class="detail-label">Email</span>
                    <span class="detail-value">${userProfile.email}</span>
                </div>
                <c:if test="${not empty userProfile.phone}">
                <div class="detail-row">
                    <span class="detail-label">Phone</span>
                    <span class="detail-value">${userProfile.phone}</span>
                </div>
                </c:if>
                <c:if test="${not empty userProfile.location}">
                <div class="detail-row">
                    <span class="detail-label">Location</span>
                    <span class="detail-value">${userProfile.location}</span>
                </div>
                </c:if>
                <c:if test="${not empty userProfile.collegeName}">
                <div class="detail-row">
                    <span class="detail-label">College</span>
                    <span class="detail-value">${userProfile.collegeName}</span>
                </div>
                </c:if>
            </div>

            <c:if test="${not empty userProfile.bio}">
            <div class="glass-panel">
                <div class="section-label">About Me</div>
                <p style="font-size:0.93rem;line-height:1.75;margin-top:8px;">${userProfile.bio}</p>
            </div>
            </c:if>
        </div>

        <%-- Right: Skills --%>
        <div class="flex flex-col gap-4">
            <div class="glass-panel">
                <div class="section-label">Skills I Can Teach</div>
                <c:choose>
                    <c:when test="${not empty mySkills}">
                        <div class="skill-tags mt-2">
                            <c:forEach var="skill" items="${mySkills}">
                                <span class="skill-tag">
                                    ${skill.skillName}
                                    <small style="opacity:0.65;font-size:0.7rem;">${skill.skillLevel}</small>
                                </span>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-sm text-muted mt-2">No skills added yet. <a href="${pageContext.request.contextPath}/student/mySkills" style="color:var(--primary-light);">Add skills →</a></p>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="glass-panel">
                <div class="section-label">Skills I Want to Learn</div>
                <c:choose>
                    <c:when test="${not empty wantedSkills}">
                        <div class="skill-tags mt-2">
                            <c:forEach var="wskill" items="${wantedSkills}">
                                <span class="skill-tag skill-tag-accent">
                                    ${wskill.skillName}
                                    <small style="opacity:0.65;font-size:0.7rem;">${wskill.priority}</small>
                                </span>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-sm text-muted mt-2">No wanted skills added yet.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
</main>

<%-- Upload toast --%>
<div id="upload-toast">📤 Uploading image...</div>

<script>
    function previewAndUpload(input) {
        var file = input.files[0];
        if (!file) return;

        // Preview instantly
        var reader = new FileReader();
        reader.onload = function(e) {
            // Replace initials div or existing img with preview
            var wrapper = input.closest('form').querySelector('.avatar-wrapper');
            var existing = wrapper.querySelector('img, .avatar-initials');
            var img = document.createElement('img');
            img.src = e.target.result;
            img.id = 'avatar-preview';
            img.style.width = '96px';
            img.style.height = '96px';
            img.style.borderRadius = '50%';
            img.style.objectFit = 'cover';
            img.style.border = '3px solid rgba(255,255,255,0.12)';
            img.style.boxShadow = '0 4px 24px rgba(108,99,255,0.4)';
            if (existing) existing.replaceWith(img);
        };
        reader.readAsDataURL(file);

        // Show toast and auto-submit
        document.getElementById('upload-toast').style.display = 'block';
        document.getElementById('img-upload-form').submit();
    }
</script>

</body>
</html>