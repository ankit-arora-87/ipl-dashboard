package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long>, JpaSpecificationExecutor<Match> {

    // getByField1OrField2Or.... Fetch list of matches where field1 = ? OR field2 = ?...
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
      String team1,
      LocalDate date1,
      LocalDate date2,
      String team2,
      LocalDate date3,
      LocalDate date4
      );

    @Query("select m from Match m where (m.team1 = :name or m.team2 = :name) and (m.date between :start_date and :end_date) order by date desc")
    List<Match> getMatchesForATeam(@Param("name") String name, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);

    default List<Match> getLatestMatchesByTeamName(String name, int count){
        return getByTeam1OrTeam2OrderByDateDesc(name, name, PageRequest.of(0, count));
    }
}
