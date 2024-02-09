package com.crocusoft.teamprojecttracker.dto.request;

import com.crocusoft.teamprojecttracker.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
public record UserRequest(

        @NotNull
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull
        @NotBlank(message = "Surname cannot be blank")
        String surname,
        @NotNull
        @NotBlank(message = "Password cannot be blank") @Length(min = 5, max = 25)
        String password,
        @NotBlank(message = "Email cannot be blank")
//        @Pattern(regexp = ".+@crocusoft\\.com$", flags = Pattern.Flag.CASE_INSENSITIVE)
        @Email(message = "Invalid email format")
        String email,
        @NotNull(message = "Roles cannot be null")
        @Size(min = 1,max =1, message = "At least one role must be present")
        @JsonProperty("roles")
        Set<String> roles

) implements Serializable {

}
