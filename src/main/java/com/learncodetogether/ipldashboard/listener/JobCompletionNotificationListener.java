package com.learncodetogether.ipldashboard.listener;

import com.learncodetogether.ipldashboard.model.Team;
import com.learncodetogether.ipldashboard.repository.DashboardRepoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;
    private final DashboardRepoInterface dashboardRepoInterface;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager, DashboardRepoInterface dashboardRepoInterface) {
        this.entityManager = entityManager;
        this.dashboardRepoInterface = dashboardRepoInterface;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Notification Current Thread " + Thread.currentThread().getName() + " --> " + Thread.currentThread().getId());
            log.info("!!! JOB FINISHED! Time to verify the results");
            setupTeams();

        }
    }
    private void setupTeams() {

        Map<String, Team> teams = new HashMap<>();
        entityManager
                .createQuery("SELECT m.team1 as name, count(*) as totalMatches from Match m group by m.team1", Object[].class)
                .getResultList()
                .stream()
                .map(data -> {
                    String name = (String) data[0];
                    Long totalMatches = (Long) data[1];
                    Team team = new Team(name, totalMatches);
                    return team;
                })
                .forEach(team -> {
                    teams.put(team.getName(),team);
                });

        entityManager
                .createQuery("SELECT m.team2 as name, count(*) as totalMatches from Match m group by m.team2", Object[].class)
                .getResultList()
                .stream()
                .map(data -> {
                    String name = (String) data[0];
                    Long totalMatches = (Long) data[1];
                    Team team;
                    if(teams.containsKey(name)){
                         team = teams.get(name);
                    } else {
                         team = new Team(name, totalMatches);
                    }
                    return team;
                })
                .forEach(team -> {
                    Long totalMatches;
                    if(teams.containsKey(team.getName())){
                        totalMatches = team.getTotalMatches() + teams.get(team.getName()).getTotalMatches();
                        team.setTotalMatches(totalMatches);
                        teams.put(team.getName(), team);
                    } else {
                        teams.put(team.getName(), team);
                    }
                });

        entityManager
                .createQuery("SELECT m.winner as name, count(*) as totalWins from Match m group by m.winner", Object[].class)
                .getResultList()
                .stream()
                .forEach(data -> {
                    String name = (String) data[0];
                    Long totalWins = (Long) data[1];
                    if(teams.containsKey(name)){
                        teams.get(name).setTotalWins(totalWins);
                    }
                });
        teams.values().forEach(team -> entityManager.persist(team));
        teams.values().forEach(team -> System.out.println(team));

    }
}