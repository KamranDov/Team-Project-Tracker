package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.response.team.CreateTeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.EditTeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.ViewTeamAllUsersResponse;
import com.crocusoft.teamprojecttracker.exception.TeamDeleteByUsersException;
import com.crocusoft.teamprojecttracker.exception.TeamNotFoundException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.TeamRepository;
import com.crocusoft.teamprojecttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final Map<String, Team> teamMap = new HashMap<>();
    private final TeamRepository teamRepository;
    private final Convert convert;
    private final UserRepository userRepository;

    public CreateTeamResponse createTeam(String createdTeamName) {
        Team newTeam = new Team();
        newTeam.setName(createdTeamName);
        return convert.teamCreate(teamRepository.save(newTeam));
    }

    public EditTeamResponse editTeam(Long id, String newTeamName) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            Team existingTeam = optionalTeam.get();
            String oldTeamName = existingTeam.getName();
            existingTeam.setName(newTeamName);
            teamRepository.save(existingTeam);

            EditTeamResponse editTeamResponse = EditTeamResponse.builder()
                    .id(existingTeam.getId())
                    .name(existingTeam.getName()).build();
            teamMap.remove(oldTeamName);
            teamMap.put(newTeamName, existingTeam);
            return editTeamResponse;
        } else throw new TeamNotFoundException("No team name found to update");
    }

    public ViewTeamAllUsersResponse viewUsersInTeamById(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            ViewTeamAllUsersResponse viewTeamAllUsersResponse = convert.teamToTeamViewAllUsersResponse(team);
            return viewTeamAllUsersResponse;
        } else throw new TeamNotFoundException("No team found with the given ID");
    }

    public void removeTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            List<User> users = userRepository.findAllByTeamId(id);
            if (!users.isEmpty()) {
                throw new TeamDeleteByUsersException("Team cannot be deleted because it has users");
            } else teamRepository.delete(team);
        } else throw new TeamNotFoundException("No team found with the given ID" + id);
    }
}

