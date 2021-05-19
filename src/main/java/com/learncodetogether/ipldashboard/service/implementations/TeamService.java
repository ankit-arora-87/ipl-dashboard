package com.learncodetogether.ipldashboard.service.implementations;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.model.Team;
import com.learncodetogether.ipldashboard.repository.MatchRepositoryInerface;
import com.learncodetogether.ipldashboard.repository.TeamRepositoryInterface;
import com.learncodetogether.ipldashboard.service.TeamServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements TeamServiceInterface {

    @Autowired
    private TeamRepositoryInterface teamRepositoryInterface;

    @Autowired
    private MatchRepositoryInerface matchRepositoryInerface;

    @Override
    public Team getTeam(String name) {
        return this.teamRepositoryInterface.findByName(name);
    }

    @Override
    public List<Match> getLatestMatches(String name, int size) {
        return this.matchRepositoryInerface.getLatestMatchesByTeamName(name, size);
    }

}
