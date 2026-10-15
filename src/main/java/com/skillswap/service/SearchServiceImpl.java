package com.skillswap.service;

import com.skillswap.dao.*;
import com.skillswap.dto.SearchResult;
import com.skillswap.dto.StudentSkill;
import com.skillswap.dto.WantedSkill;

import java.util.List;

public class SearchServiceImpl implements SearchService {
    private SearchDAO searchDAO = new SearchDAOImpl();
    private StudentSkillDAO studentSkillDAO = new StudentSkillDAOImpl();
    private WantedSkillDAO wantedSkillDAO = new WantedSkillDAOImpl();

    @Override
    public List<SearchResult> searchAndMatch(int searcherId, String skillKeyword, String location, String college, int offset, int limit) {
        List<SearchResult> searchResults = searchDAO.searchStudents(searcherId, skillKeyword, location, college, offset, limit);
        List<StudentSkill> searcherCanTeach = studentSkillDAO.getSkillsByUserId(searcherId);
        
        for (SearchResult result : searchResults) {
            result.setMatchType("Potential Match");
            List<WantedSkill> resultWants = wantedSkillDAO.getWantedSkillsByUserId(result.getUserId());
            
            for (WantedSkill wanted : resultWants) {
                for (StudentSkill iTeach : searcherCanTeach) {
                    if (wanted.getSkillId() == iTeach.getSkillId()) {
                        result.setMatchType("Perfect Match");
                        break;
                    }
                }
                if ("Perfect Match".equals(result.getMatchType())) break;
            }
        }
        
        return searchResults;
    }

    @Override
    public int getTotalSearchPages(int searcherId, String skillKeyword, String location, String college, int pageSize) {
        int totalRecords = searchDAO.countStudents(searcherId, skillKeyword, location, college);
        return (int) Math.ceil((double) totalRecords / pageSize);
    }
}
