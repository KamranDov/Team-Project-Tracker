package com.crocusoft.teamprojecttracker.dto.response.team;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditTeamResponse implements Serializable {

    Long id;
    String name;
}
