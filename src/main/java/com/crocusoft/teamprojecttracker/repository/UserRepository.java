package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
