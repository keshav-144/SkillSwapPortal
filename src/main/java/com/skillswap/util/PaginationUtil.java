package com.skillswap.util;

public class PaginationUtil {
    
    /**
     * Calculates the SQL OFFSET for the LIMIT clause based on the current page.
     */
    public static int getOffset(int pageNumber, int recordsPerPage) {
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        return (pageNumber - 1) * recordsPerPage;
    }
    
    /**
     * Calculates the total number of pages required to display all records.
     */
    public static int getTotalPages(int totalRecords, int recordsPerPage) {
        if (totalRecords == 0) return 1;
        return (int) Math.ceil((double) totalRecords / recordsPerPage);
    }
}
