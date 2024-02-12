package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.request.TeamRequest;
import com.crocusoft.teamprojecttracker.dto.response.TeamResponse;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.createTeam(teamRequest), CREATED);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<Map<String, Team>> editTeam(String changeTeamName, @RequestBody TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.editTeam(changeTeamName, teamRequest), CREATED);
    }

    @GetMapping("/{team-name}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<List<User>> getAllEmployeeByTeamName(@PathVariable("team-name") String teamName) throws Exception {
        return new ResponseEntity<>(teamService.findUsersByTeamName(teamName), OK);

    }
}
