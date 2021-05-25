package com.learncodetogether.ipldashboard.controller;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.model.Team;
import com.learncodetogether.ipldashboard.service.MatchServiceInterface;
import com.learncodetogether.ipldashboard.service.TeamServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamServiceInterface teamServiceInterface;

    @Autowired
    private MatchServiceInterface matchServiceInterface;

    @GetMapping
    List<Team> getTeams(){
        return this.teamServiceInterface.getTeams();
    }

    @GetMapping("/{name}/{size}")
    Team getTeam(@PathVariable String name,@PathVariable int size){
        Team team =  this.teamServiceInterface.getTeam(name);
        team.setLatestMatches(this.teamServiceInterface.getLatestMatches(name, size));
        return team;

    }

    @GetMapping("/{name}/matches")
    List<Match> getMatchesForATeam(
            @PathVariable String name,
            @RequestParam(required = false, defaultValue = "-1") int year,
            @RequestParam(required = false, name = "against", defaultValue = "all") String againstTeam,
            @RequestParam(required = false, defaultValue = "all", name = "result") String matchResult){

        List<Match> matches =  this.matchServiceInterface.getMatchesForATeam(name, year, againstTeam, matchResult);
//        List<Match> matches =  this.matchServiceInterface.getMatchesForATeamWithFilters(name, year, againstTeam, matchResult);

        return matches;
    }
}
