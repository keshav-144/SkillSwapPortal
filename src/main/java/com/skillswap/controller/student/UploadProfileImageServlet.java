package com.skillswap.controller.student;

import com.skillswap.dto.User;
import com.skillswap.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

/**
 * Handles profile image uploads for students.
 * Saves image to /uploads/profiles/ inside the webapp and updates the DB.
 */
@WebServlet("/student/uploadProfileImage")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,       // 1 MB threshold before writing to disk
    maxFileSize       = 5 * 1024 * 1024,   // 5 MB max per file
    maxRequestSize    = 6 * 1024 * 1024    // 6 MB max request
)
public class UploadProfileImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User sessionUser = (User) request.getSession().getAttribute("loggedInUser");
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Part filePart = request.getPart("profileImage");
        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/student/profile?error=No image selected.");
            return;
        }

        // Validate file type
        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            response.sendRedirect(request.getContextPath() + "/student/profile?error=Only image files are allowed.");
            return;
        }

        // Determine file extension
        String originalFileName = filePart.getSubmittedFileName();
        String ext = originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : ".jpg";

        // Generate a unique filename
        String newFileName = "user_" + sessionUser.getUserId() + "_" + UUID.randomUUID().toString().substring(0, 8) + ext;

        // Upload directory: webapp/uploads/profiles/
        String uploadDir = getServletContext().getRealPath("/uploads/profiles/");
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Save file
        File savedFile = new File(dir, newFileName);
        try (InputStream input = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(savedFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        // Save relative path to DB
        String relPath = "uploads/profiles/" + newFileName;
        boolean updated = updateProfileImageInDB(sessionUser.getUserId(), relPath);

        if (updated) {
            sessionUser.setProfileImage(relPath);
            request.getSession().setAttribute("loggedInUser", sessionUser);
            response.sendRedirect(request.getContextPath() + "/student/profile?success=Profile image updated!");
        } else {
            response.sendRedirect(request.getContextPath() + "/student/profile?error=Failed to save image. Please try again.");
        }
    }

    private boolean updateProfileImageInDB(int userId, String imagePath) {
        String sql = "UPDATE users SET profile_image = ? WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, imagePath);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
