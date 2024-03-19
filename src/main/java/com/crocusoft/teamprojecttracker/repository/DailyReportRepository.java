package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {


}