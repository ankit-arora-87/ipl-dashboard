package com.learncodetogether.ipldashboard.service.implementations;

import com.learncodetogether.ipldashboard.model.Match;
import com.learncodetogether.ipldashboard.repository.MatchRepository;
import com.learncodetogether.ipldashboard.service.MatchServiceInterface;
import com.learncodetogether.ipldashboard.specification.MatchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService implements MatchServiceInterface {

    @Autowired
    private MatchRepository matchRepositoryInterface;

    @Override
    public List<Match> getMatchesForATeamWithFilters(String name, int year, String againstTeam, String matchResult) {
        if(year == -1){
            year = 2020;
        }
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);


        System.out.println("Year: " + year);
        System.out.println("Against Team: " + againstTeam);
        System.out.println("Match Result: " + matchResult);
        return this.matchRepositoryInterface.getMatchesForATeam(name, startDate, endDate);

//        return this.matchRepositoryInerface.getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
//                name,
//                startDate,
//                endDate,
//                name,
//                startDate,
//                endDate
//        );
    }

    @Override
    public List<Match> getMatchesForATeam(String name, int year, String againstTeam, String matchResult) {
        System.out.println("MS - Name: " + name);
        System.out.println("MS - Year: " + year);
        System.out.println("MS - Against Team: " + againstTeam);
        System.out.println("MS - Match Result: " + matchResult);

        Specification whereSpec = null;
        if(!name.equalsIgnoreCase("")) {
            Specification<Match> specName = MatchSpecification.hasTeamName(name);
            whereSpec = Specification.where(specName);
            System.out.println("Team Name: " + whereSpec);
        }

        if(year == -1) {
            year = 2020;
        }
        if(year >= 2008 && year <= 2020){
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year + 1, 1, 1);
            Specification<Match> specYear = MatchSpecification.hasPlayedBetween(startDate, endDate);
            whereSpec = whereSpec.and(specYear);
            System.out.println("Year: " + specYear + "--" +whereSpec);

        }

        if(!againstTeam.equalsIgnoreCase("all")){
            Specification<Match> specAgainstTeam = MatchSpecification.hasPlayedAgainst(name, againstTeam);
            whereSpec = whereSpec.and(specAgainstTeam);
            System.out.println("Against: " + specAgainstTeam + "--" +whereSpec);

        }

        if(matchResult.equalsIgnoreCase("won") || matchResult.equalsIgnoreCase("lost")){
            Specification<Match> specMatchResult = MatchSpecification.hasWonOrLost(name, matchResult);
            whereSpec = whereSpec.and(specMatchResult);
            System.out.println("Match Result: " + specMatchResult + "--" +whereSpec);
        }
        System.out.println("Final: " + "--" +whereSpec);

        return this.matchRepositoryInterface.findAll(whereSpec);

//        return this.matchRepositoryInterface.getMatchesForATeamWithFilters(name, year, againstTeam, matchResult);
    }
}
