package com.skillswap.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return isNotNullOrEmpty(email) && Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
    
    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }
    
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
}
