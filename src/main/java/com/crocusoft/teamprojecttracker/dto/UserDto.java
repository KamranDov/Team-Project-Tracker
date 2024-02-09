package com.crocusoft.teamprojecttracker.dto;

import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.model.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Builder
public record UserDto(@NotNull @NotBlank(message = "Name cannot be blank") String name,
                      @NotNull @NotBlank String surname,
                      @NotNull @NotBlank(message = "Password cannot be blank") @Length(min = 5, max = 25) String password,
                      @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email,
                      UserActionStatus userActionStatus

) implements Serializable {

}