package com.crocusoft.teamprojecttracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public record AuthRequest(
        @NotNull
        @JsonProperty("email")
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required.")
        @Pattern(regexp = ".+@crocusoft\\.com$", message = "Email must end with @crocusoft.com.")
        String email,

        @JsonProperty("password")
        @NotNull
        @NotBlank(message = "Password cannot be blank")
        @Length(min = 5, max = 25)
        String password

) implements Serializable {
}
