package com.crocusoft.teamprojecttracker.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        @JsonProperty("name")
        String name,
        @JsonProperty("surname")
        String surname,
        @JsonProperty("password")
        String password,
        @JsonProperty("email")
        String email,
        @JsonProperty("roles")
        List<String> roles

) {
}
