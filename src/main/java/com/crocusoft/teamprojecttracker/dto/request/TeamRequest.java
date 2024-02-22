package com.crocusoft.teamprojecttracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TeamRequest(@NotNull
                          @NotBlank(message = "Team name cannot be blank.")
                          @JsonProperty("name")
                          String name) {
}
