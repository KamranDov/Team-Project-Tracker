package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);

    List<Team> findAllByNameIn(List<String> teams);

//    List<Team> findByUsersIn(List<User> usersInProject);
}
