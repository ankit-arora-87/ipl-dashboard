package com.learncodetogether.ipldashboard.service;

import com.learncodetogether.ipldashboard.model.Match;

import java.util.List;

public interface DashboardServiceInterface {
    List<Match> getMatches(int page, int size);
}
