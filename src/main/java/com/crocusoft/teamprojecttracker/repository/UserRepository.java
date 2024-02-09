package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByEmailAAndUserActionStatus(String email, String status);

    Optional<List<User>> findAllByTeamName(String teamName);

//    User findByRole(Role role);

}
