package com.crocusoft.teamprojecttracker.dto.response.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeamResponse implements Serializable {

    @JsonProperty("name")
    String name;
}
