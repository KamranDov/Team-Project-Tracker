package com.crocusoft.teamprojecttracker.dto.response.team;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditTeamResponse {

    Long id;
    String name;
}
