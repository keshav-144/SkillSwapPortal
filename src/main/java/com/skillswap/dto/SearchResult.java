package com.skillswap.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private int userId;
    private String fullName;
    private String collegeName;
    private String location;
    
    private List<String> taughtSkills = new ArrayList<>();
    private List<String> wantedSkills = new ArrayList<>();
    
    private String matchType; // "Perfect Match" or "Potential Match"
    private String gender;

    public SearchResult() {}

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getTaughtSkills() { return taughtSkills; }
    public void setTaughtSkills(List<String> taughtSkills) { this.taughtSkills = taughtSkills; }

    public List<String> getWantedSkills() { return wantedSkills; }
    public void setWantedSkills(List<String> wantedSkills) { this.wantedSkills = wantedSkills; }

    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }
}
