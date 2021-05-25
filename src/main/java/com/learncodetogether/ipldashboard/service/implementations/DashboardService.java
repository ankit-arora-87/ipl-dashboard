package com.learncodetogether.ipldashboard.service.implementations;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.repository.DashboardRepoInterface;
import com.learncodetogether.ipldashboard.service.DashboardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService implements DashboardServiceInterface {

    @Autowired
    private DashboardRepoInterface dashboardRepoInterface;

    @Override
    public List<Match> getMatches(int page, int size) {
        return this.dashboardRepoInterface.getMatches(page, size);
    }
}
