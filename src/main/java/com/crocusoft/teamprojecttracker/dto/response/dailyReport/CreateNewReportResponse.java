package com.crocusoft.teamprojecttracker.dto.response.dailyReport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNewReportResponse {

    @JsonProperty("user ID")
    Long userId;

    @JsonProperty("project ID")
    Long projectId;

    @JsonProperty("information about the work done")
    String text;



}
