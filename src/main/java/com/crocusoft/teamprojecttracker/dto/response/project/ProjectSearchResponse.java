package com.crocusoft.teamprojecttracker.dto.response.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectSearchResponse {

    @JsonProperty("project id")
    Long id;

    @JsonProperty("project name")
    String projectName;

    @JsonProperty("project description")
    List<ProjectFilterResponseItem> projectFilterResponseItems;

}
