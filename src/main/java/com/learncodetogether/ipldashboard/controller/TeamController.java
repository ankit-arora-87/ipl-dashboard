package com.learncodetogether.ipldashboard.controller;

import com.learncodetogether.ipldashboard.model.Team;
import com.learncodetogether.ipldashboard.service.TeamServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    private TeamServiceInterface teamServiceInterface;

    @GetMapping("/{name}/{size}")
    Team getTeam(@PathVariable String name,@PathVariable int size){
        Team team =  this.teamServiceInterface.getTeam(name);
        team.setLatestMatches(this.teamServiceInterface.getLatestMatches(name, size));
        return team;

    }
}
