package com.learncodetogether.ipldashboard.controller;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.service.DashboardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    DashboardServiceInterface dashboardServiceInterface;

    @GetMapping
    public List<Match> matches(@RequestParam int page, @RequestParam int size){
        return dashboardServiceInterface.getMatches(page, size);
    }
}
