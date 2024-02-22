package com.crocusoft.teamprojecttracker.dto.response.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamByUsersResponse {

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;
}
