package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.request.TeamRequest;
import com.crocusoft.teamprojecttracker.dto.response.TeamResponse;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.service.TeamService;
import com.crocusoft.teamprojecttracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//@CrossOrigin("*")
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeamResponse> createTeam(TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.createTeam(teamRequest), CREATED);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TeamResponse> editTeam(String teamName, TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.editTeam(teamName, teamRequest), CREATED);
    }

    @GetMapping("/{team-name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllEmployeeByTeamName(@PathVariable("team-name") String teamName) throws Exception {
        return new ResponseEntity<>(teamService.findUsersByTeamName(teamName), OK);

    }
}
