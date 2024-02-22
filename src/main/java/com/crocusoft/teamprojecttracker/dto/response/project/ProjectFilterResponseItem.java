package com.crocusoft.teamprojecttracker.dto.response.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectFilterResponseItem {

    @JsonProperty("employee id")
    Long employeeId;

    @JsonProperty("employee name")
    String employeeName;

    @JsonProperty("employee surname")
    String employeeSurname;

    @JsonProperty("employee email")
    String employeeEmail;

    @JsonProperty("roles")
    String roles;

    @JsonProperty("team id")
    Long teamId;

    @JsonProperty("team name")
    String teamName;
}
