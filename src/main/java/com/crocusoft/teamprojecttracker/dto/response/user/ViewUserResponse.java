package com.crocusoft.teamprojecttracker.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewUserResponse {

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;

    @JsonProperty("email")
    String email;

    @JsonProperty("roles")
    List<String> roles;

    @JsonProperty("team name")
    String teamName;

    @JsonProperty("all projects name")
    List<String> projects;
}
