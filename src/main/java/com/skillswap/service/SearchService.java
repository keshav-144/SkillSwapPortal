package com.skillswap.service;

import com.skillswap.dto.SearchResult;
import java.util.List;

public interface SearchService {
    /**
     * Executes a search and applies the Skill Matching algorithm 
     * to determine if each result is a Perfect or Potential match.
     */
    List<SearchResult> searchAndMatch(int searcherId, String skillKeyword, String location, String college, int offset, int limit);
    int getTotalSearchPages(int searcherId, String skillKeyword, String location, String college, int pageSize);
}
