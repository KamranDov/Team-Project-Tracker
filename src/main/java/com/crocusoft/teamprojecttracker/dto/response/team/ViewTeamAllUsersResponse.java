package com.crocusoft.teamprojecttracker.dto.response.team;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewTeamAllUsersResponse implements Serializable {

    String name;
    List<TeamByUsersResponse> users;
}
