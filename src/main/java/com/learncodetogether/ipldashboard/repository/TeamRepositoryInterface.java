package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepositoryInterface extends CrudRepository<Team, Long> {

    Team findByName(String name);
}
