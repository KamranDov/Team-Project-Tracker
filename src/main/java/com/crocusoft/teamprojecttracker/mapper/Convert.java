package com.crocusoft.teamprojecttracker.mapper;

import com.crocusoft.teamprojecttracker.dto.response.TeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.UserResponse;
import com.crocusoft.teamprojecttracker.model.Role;
import com.crocusoft.teamprojecttracker.model.Team;
import com.crocusoft.teamprojecttracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class Convert {
    public UserResponse userToUserResponse(User user) {

        return new UserResponse(
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
//user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                user.getAuthorities().stream()
                        .filter(authority -> authority instanceof Role)
                        .map(authority -> ((Role) authority).getName())
                        .toList());

    }
    public TeamResponse teamToTeamResponse(Team team) {

        return new TeamResponse(
                team.getName()
        );
    }
}
