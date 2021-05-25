package com.learncodetogether.ipldashboard.specification;

import com.learncodetogether.ipldashboard.model.Match;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class MatchSpecification {

    public static Specification<Match> hasTeamName(String team) {
        return new Specification<Match>() {
            @Override
            public Predicate toPredicate(Root<Match> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicateForTeam1
                        = cb.equal(root.get("team1"), team);
                Predicate predicateForTeam2
                        = cb.equal(root.get("team2"), team);
                return cb.or(
                        predicateForTeam1,
                        predicateForTeam2
                );
            }
        };
    }

    public static Specification<Match> hasPlayedBetween(LocalDate startDate, LocalDate endDate) {
        return new Specification<Match>() {
            @Override
            public Predicate toPredicate(Root<Match> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate yearPredicate = cb.between(root.get("date"), startDate, endDate);
                return yearPredicate;
            }
        };
    }

    public static Specification<Match> hasPlayedAgainst(String team, String againstTeam) {
        return new Specification<Match>() {
            @Override
            public Predicate toPredicate(Root<Match> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicateForTeam1
                        = cb.equal(root.get("team1"), team);
                Predicate predicateForTeam2
                        = cb.equal(root.get("team2"), team);
                Predicate predicateForTeam1AgainstTeam
                        = cb.equal(root.get("team2"), againstTeam);
                Predicate predicateForTeam2AgainstTeam
                        = cb.equal(root.get("team1"), againstTeam);
                Predicate predicateForAgainstTeam
                        = cb.or(
                                cb.and(predicateForTeam1, predicateForTeam1AgainstTeam),
                                cb.and(predicateForTeam2, predicateForTeam2AgainstTeam)
                );
                return predicateForAgainstTeam;
            }
        };
    }

    public static Specification<Match> hasWonOrLost(String team, String matchResult) {
        return new Specification<Match>() {
            @Override
            public Predicate toPredicate(Root<Match> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate matchResultPredicate = null;
                if(matchResult.equalsIgnoreCase("won")){
                    matchResultPredicate = cb.equal(root.get("winner"), team);
                } else  if(matchResult.equalsIgnoreCase("lost")){
                    matchResultPredicate = cb.notEqual(root.get("winner"), team);
                }
                return matchResultPredicate;


            }
        };
    }


}
