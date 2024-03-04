package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends CrudRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findByOtpAndUserEmail(String otp, String email);

}
