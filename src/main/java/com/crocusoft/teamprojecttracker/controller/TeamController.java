package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.response.team.CreateTeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.EditTeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.ViewTeamAllUsersResponse;
import com.crocusoft.teamprojecttracker.exception.TeamNotFoundException;
import com.crocusoft.teamprojecttracker.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<CreateTeamResponse> createTeam(String createdTeamName) {
        return new ResponseEntity<>(teamService.createTeam(createdTeamName), CREATED);
    }

    @PutMapping("/edit{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<EditTeamResponse> editTeam(@PathVariable("id") Long id, String newTeamName) throws TeamNotFoundException {
        return new ResponseEntity<>(teamService.editTeam(id, newTeamName), CREATED);
    }

    @GetMapping("/view{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'HEAD')")
    public ResponseEntity<ViewTeamAllUsersResponse> getAllEmployeeByTeamName(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(teamService.viewUsersInTeamById(id), OK);
    }

    @DeleteMapping("/remove{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<String> removeTeam(@PathVariable("id") Long id) {
        teamService.removeTeam(id);
        return ResponseEntity.ok("Team deleted successfully");

    }
}
