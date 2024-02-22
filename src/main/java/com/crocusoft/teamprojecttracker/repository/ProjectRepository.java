package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String projectName);

    List<Project> findAllByNameIn(List<String> projects);

//    List<Project> findAllById(Long id);


}
