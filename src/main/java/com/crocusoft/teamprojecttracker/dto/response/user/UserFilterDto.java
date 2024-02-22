package com.crocusoft.teamprojecttracker.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserFilterDto {

    @JsonProperty("employee id")
    Long employeeId;

    @JsonProperty("employee name")
    String employeeName;

    @JsonProperty("employee surname")
    String employeeSurname;

    @JsonProperty("employee email")
    String employeeEmail;

    @JsonProperty("role name")
    String roleName;

    @JsonProperty("team ID")
    Long teamId;

    @JsonProperty("project IDs")
    List<Long> projectId;
}
