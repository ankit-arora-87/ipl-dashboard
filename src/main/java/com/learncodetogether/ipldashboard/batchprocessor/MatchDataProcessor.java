
package com.learncodetogether.ipldashboard.batchprocessor;

import com.learncodetogether.ipldashboard.data.MatchInputData;
import com.learncodetogether.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInputData, Match> {

  private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);


  @Override
  public Match process(MatchInputData item) throws Exception {
    Match match = new Match();
    match.setId(Long.parseLong(item.getId()));
    match.setCity(item.getCity());
    match.setDate(LocalDate.parse(item.getDate()));
    match.setPlayerOfMatch(item.getPlayerOfMatch());
    match.setVenue(item.getVenue());

    // To find out team 1 & team 2 on basis of their innings & toss decision
    String firstInningsTeam, secondInningsTeam;

    if(item.getTossDecision().equalsIgnoreCase("bat")){
      firstInningsTeam = item.getTossWinner();
      secondInningsTeam = (item.getTossWinner().equals(item.getTeam1()))?item.getTeam2():item.getTeam1();
    } else {
      firstInningsTeam = (item.getTossWinner().equals(item.getTeam1()))?item.getTeam2():item.getTeam1();
      secondInningsTeam = item.getTossWinner();
    }

    match.setTeam1(firstInningsTeam);
    match.setTeam2(secondInningsTeam);
    match.setTossWinner(item.getTossWinner());
    match.setTossDecision(item.getTossDecision());
    match.setTossWinner(item.getWinner());
    match.setWinner(item.getWinner());
    match.setResult(item.getResult());
    match.setResultMargin(item.getResultMargin());
    match.setEliminator(item.getEliminator());
    match.setUmpire1(item.getUmpire1());
    match.setUmpire2(item.getUmpire2());

    log.info("Converting (" + item + ") into (" + match + ")");

    return match;
  }
}