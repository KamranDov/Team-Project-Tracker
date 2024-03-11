package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.constants.DefaultPageValues;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.CreateNewReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.DailyFilterResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.ViewDailyReportResponse;
import com.crocusoft.teamprojecttracker.exception.ReportCreationTimeExpiredException;
import com.crocusoft.teamprojecttracker.exception.ReportNotFoundException;
import com.crocusoft.teamprojecttracker.exception.UserNotFoundException;
import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.service.DailyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/daily-report")
public class DailyReportController {
    private final DailyReportService dailyReportService;

    @PutMapping("/edit-report{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DailyReport> editReport(@PathVariable("id") Long reportId, String text) throws ReportCreationTimeExpiredException, UserNotFoundException {
        return new ResponseEntity<>(dailyReportService.updateReport(reportId, text), OK);
    }

    @PostMapping("/new-report")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CreateNewReportResponse> newReport(Long employeeId,
                                                             String text,
                                                             @RequestParam(value = "project name") String projects) {
        return new ResponseEntity<>(dailyReportService.newReport(employeeId, text, projects), CREATED);
    }

    @GetMapping("/view-report{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'HEAD', 'USER')")
    public ResponseEntity<ViewDailyReportResponse> viewReport(@PathVariable("id") Long reportId) throws ReportNotFoundException {
        return new ResponseEntity<>(dailyReportService.viewReport(reportId), OK);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<DailyFilterResponse> filter(
            @RequestParam(value = "page number", defaultValue = DefaultPageValues.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "page count", defaultValue = DefaultPageValues.PAGE_COUNT) Integer pageCount,
            @RequestParam(value = "employees ID") List<Long> employeeIds) {
        return new ResponseEntity<>(dailyReportService.filterReport(pageNumber, pageCount, employeeIds), OK);
    }
}