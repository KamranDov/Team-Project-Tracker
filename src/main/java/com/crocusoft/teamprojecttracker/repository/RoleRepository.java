package com.crocusoft.teamprojecttracker.repository;

import com.crocusoft.teamprojecttracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByName(String name);

    Set<Role> findAllByNameIn(Collection<String> name);

}
