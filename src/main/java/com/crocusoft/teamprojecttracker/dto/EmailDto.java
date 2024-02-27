package com.crocusoft.teamprojecttracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDto {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email;
    String subject;
    String message;
}
