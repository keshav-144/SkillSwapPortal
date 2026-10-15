<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Skill - SkillSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <main class="container animate-fade-in" style="max-width: 600px; margin-top: 50px;">
        <div class="glass-panel">
            <h2 class="mb-4">Add a Skill You Can Teach</h2>
            <form action="${pageContext.request.contextPath}/student/addSkill" method="post" class="flex gap-4" style="flex-direction: column;">
                <div>
                    <label style="color: var(--text-muted); display: block; margin-bottom: 5px;">Skill Name</label>
                    <input type="text" name="skillName" class="form-control" placeholder="e.g. Java Programming" required>
                </div>
                <div>
                    <label style="color: var(--text-muted); display: block; margin-bottom: 5px;">Category</label>
                    <input type="text" name="category" class="form-control" placeholder="e.g. Programming">
                </div>
                <div>
                    <label style="color: var(--text-muted); display: block; margin-bottom: 5px;">Description</label>
                    <textarea name="description" class="form-control" rows="3" placeholder="Briefly describe the skill..."></textarea>
                </div>
                <div>
                    <label style="color: var(--text-muted); display: block; margin-bottom: 5px;">Your Proficiency Level</label>
                    <select name="proficiencyLevel" class="form-control" required>
                        <option value="BEGINNER">Beginner</option>
                        <option value="INTERMEDIATE">Intermediate</option>
                        <option value="ADVANCED">Advanced</option>
                    </select>
                </div>
                <div>
                    <label style="color: var(--text-muted); display: block; margin-bottom: 5px;">Experience</label>
                    <input type="text" name="experience" class="form-control" placeholder="e.g. 2 years, self-taught, etc.">
                </div>
                <div class="flex gap-4 mt-4">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Save Skill</button>
                    <a href="${pageContext.request.contextPath}/student/mySkills" class="btn btn-outline" style="flex: 1; text-align: center;">Cancel</a>
                </div>
            </form>
        </div>
    </main>
</body>
</html>