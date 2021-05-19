package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepoInterface extends JpaRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable page);

    default List<Match> getMatches(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return (List<Match>) this.findAll();

    }

    default List<Match> getTeamsByName(String teamName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);

    }
}
