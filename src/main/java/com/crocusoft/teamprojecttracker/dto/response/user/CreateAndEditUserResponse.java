package com.crocusoft.teamprojecttracker.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAndEditUserResponse {

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;

    @JsonProperty("email")
    String email;

    @JsonProperty("role name")
    String roleName;

    @JsonProperty("team name")
    String teamName;
}
