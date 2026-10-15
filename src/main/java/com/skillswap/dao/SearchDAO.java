package com.skillswap.dao;

import com.skillswap.dto.SearchResult;
import java.util.List;

public interface SearchDAO {
    /**
     * Dynamically searches for students teaching a specific skill, 
     * optionally filtered by location or college.
     */
    List<SearchResult> searchStudents(int searcherId, String skillKeyword, String location, String college, int offset, int limit);
    int countStudents(int searcherId, String skillKeyword, String location, String college);
}
