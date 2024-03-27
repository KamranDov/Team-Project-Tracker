package com.crocusoft.teamprojecttracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @JsonProperty("name")
    @NotNull
    @NotBlank(message = "Name cannot be blank")
    String name;

    @JsonProperty("surname")
    @NotNull
    @NotBlank(message = "Surname cannot be blank")
    String surname;

    @NotNull
    @JsonProperty("email")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = ".+@crocusoft\\.com$", message = "Email must end with @crocusoft.com.")
    String email;

    @JsonProperty("password")
    @NotNull
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, max = 25)
    String password;


    @JsonProperty("roles")
    @NotNull(message = "Roles cannot be null")
    @Size(min = 1, max = 1, message = "At least one role must be present")
    Set<String> roles;

    @JsonProperty("team_id")
    @NotNull(message = "Team name cannot be null")
    Long teamId;
}
