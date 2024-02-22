package com.crocusoft.teamprojecttracker.dto.response.dailyReport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewDailyReportResponse {

    @JsonProperty("daily report ID")
    Long id;

    @JsonProperty("daily report description")
    String description;

    @JsonProperty("daily report created at")
    LocalDate createdAt;

    @JsonProperty("employee name")
    String employeeName;

    @JsonProperty("employee surname")
    String employeeSurname;

    @JsonProperty("project ID")
    Long projectId;
}
