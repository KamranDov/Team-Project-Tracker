package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByTeamId(Long id);

    List<User> findByIdIn(List<Long> users);

    List<User> findAllByProjectsId(Long id);

    User findByName(String name);

    User findByNameOrSurname(String name, String surname);


//    List<User> filterByEmployeeNameAndSurname(String name, String surname);

//    List<User> findByTeamsIn(List<Team> teamsList);
//
//    List<User> findByProjectsIn(List<Project> projectsList);
//
//    List<User> findByName(String name);
//
//    List<User> findBySurname(String surname);
}
