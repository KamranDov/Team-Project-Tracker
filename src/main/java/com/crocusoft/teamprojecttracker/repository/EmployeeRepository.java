package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmployeeRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
