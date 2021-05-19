package com.learncodetogether.ipldashboard.service;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.model.Team;

import java.util.List;

public interface TeamServiceInterface {
    Team getTeam(String name);

    List<Match> getLatestMatches(String name, int size);
}
