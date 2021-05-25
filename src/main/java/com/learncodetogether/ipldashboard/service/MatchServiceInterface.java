package com.learncodetogether.ipldashboard.service;

import com.learncodetogether.ipldashboard.model.Match;

import java.util.List;

public interface MatchServiceInterface {

    List<Match> getMatchesForATeam(String name, int year, String againstTeam, String matchResult);
    List<Match> getMatchesForATeamWithFilters(String name, int year, String againstTeam, String matchResult);
}
