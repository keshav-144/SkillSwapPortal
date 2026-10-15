<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Skills - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="nav-brand">SkillSwap ✨</a>
        <div class="nav-links">
            
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/skills">Manage Skills</a>
            <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline" style="padding: 6px 16px;">Logout</a>
        
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
        
        
        <div class="flex justify-between items-center mb-4">
            <h1>Manage System Skills</h1>
            <button class="btn btn-primary" onclick="document.getElementById('addSkillForm').style.display='block'">+ Add New Skill</button>
        </div>
        
        <div class="glass-panel mb-4" id="addSkillForm" style="display: none;">
            <h3>Add New Skill</h3>
            <form action="${pageContext.request.contextPath}/admin/addSkill" method="post" class="flex gap-4 items-center mt-4">
                <div class="form-group" style="margin-bottom: 0; flex: 1;">
                    <input type="text" name="skillName" class="form-control" placeholder="Skill Name" required>
                </div>
                <div class="form-group" style="margin-bottom: 0; flex: 2;">
                    <input type="text" name="category" class="form-control" placeholder="Category" required>
                </div>
                <button type="submit" class="btn btn-primary">Save Skill</button>
            </form>
        </div>

        <div class="glass-panel">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="skill" items="${skills}">
                            <tr>
                                <td>${skill.id}</td>
                                <td>${skill.name}</td>
                                <td><span class="badge badge-success">${skill.category}</span></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/skills/delete" method="post" style="display:inline;">
                                        <input type="hidden" name="skillId" value="${skill.id}">
                                        <button type="submit" class="btn btn-outline" style="padding: 4px 8px; font-size: 0.8rem; border-color: #ef4444; color: #ef4444;">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    
    </main>
</body>
</html>\n
