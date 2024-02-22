package com.crocusoft.teamprojecttracker.dto.response.dailyReport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DailyFilterResponse {

    @JsonProperty("employees ID")
    List<Long> employeesId;

    @JsonProperty("projects name")
    List<String> projectsName;

    @JsonProperty("dates")
    List<LocalDate> dates;

    @JsonProperty("total pages")
    Integer totalPages;

    @JsonProperty("total elements")
    Long totalElements;

    @JsonProperty("has next")
    Boolean hasNext;
}
