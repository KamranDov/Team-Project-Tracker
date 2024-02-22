package com.crocusoft.teamprojecttracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AuthRequest(
        @NotNull
        @NotBlank(message = "Email is required.")
        @JsonProperty("email")
        String email,
        @JsonProperty("password")
        @NotNull
        @NotBlank(message = "Password cannot be blank")
        @Length(min = 5, max = 25)
        String password

) {
}
