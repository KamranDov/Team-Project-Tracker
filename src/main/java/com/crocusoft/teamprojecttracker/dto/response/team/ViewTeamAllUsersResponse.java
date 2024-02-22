package com.crocusoft.teamprojecttracker.dto.response.team;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewTeamAllUsersResponse {

    String name;
    List<TeamByUsersResponse> users;
}
