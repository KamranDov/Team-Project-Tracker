package com.crocusoft.teamprojecttracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDto {

    @NotNull
    @JsonProperty("email")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = ".+@crocusoft\\.com$", message = "Email must end with @crocusoft.com.")
    String email;
    String subject;
    String message;
}
