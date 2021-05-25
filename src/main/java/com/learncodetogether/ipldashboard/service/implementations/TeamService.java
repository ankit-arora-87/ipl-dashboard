package com.learncodetogether.ipldashboard.service.implementations;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.model.Team;
import com.learncodetogether.ipldashboard.repository.MatchRepository;
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
    private MatchRepository matchRepositoryInterface;

    @Override
    public List<Team> getTeams() {
        return (List<Team>) this.teamRepositoryInterface.findAll();
    }

    @Override
    public Team getTeam(String name) {
        return this.teamRepositoryInterface.findByName(name);
    }

    @Override
    public List<Match> getLatestMatches(String name, int size) {
        return this.matchRepositoryInterface.getLatestMatchesByTeamName(name, size);
    }

}
