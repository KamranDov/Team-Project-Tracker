package com.crocusoft.teamprojecttracker.dto.response.project;

import com.crocusoft.teamprojecttracker.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProjectResponse {

    @NotNull
    @NotBlank(message = "Project name cannot be blank.")
    @JsonProperty("name")
    String name;

    @NotNull
    @NotBlank(message = "Users name cannot be blank.")
    @JsonProperty("users")
    List<Long> users;
}
