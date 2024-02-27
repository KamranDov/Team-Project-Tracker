package com.crocusoft.teamprojecttracker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {

    @JsonProperty("new password")
    String password;

    @JsonProperty("confirm new password")
    String confirmPassword;
}
