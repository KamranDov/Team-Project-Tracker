package com.crocusoft.teamprojecttracker.dto;

import com.crocusoft.teamprojecttracker.model.DailyReport;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link DailyReport}
 */
public record DailyReportDto
        (String name,
         String surname,
         String description,
         LocalDate updateTime) implements Serializable {
}