package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.Match;

import java.util.List;

public interface MatchCustomRepository {

    List<Match> getMatchesForATeamWithFilters(
           String name,
           int year,
           String againstTeam,
           String matchResult
    );
}
