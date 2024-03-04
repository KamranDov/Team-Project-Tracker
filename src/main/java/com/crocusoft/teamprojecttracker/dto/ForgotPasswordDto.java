package com.crocusoft.teamprojecttracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordDto implements Serializable {

    String otp;
    Date lifeTime;
    Long userId;
}
