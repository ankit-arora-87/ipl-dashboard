package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepositoryInerface extends CrudRepository<Match, Long> {

    // getByField1OrField2Or.... Fetch list of matches where field1 = ? OR field2 = ?...
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    default List<Match> getLatestMatchesByTeamName(String name, int count){
        return getByTeam1OrTeam2OrderByDateDesc(name, name, PageRequest.of(0, count));
    }
}
