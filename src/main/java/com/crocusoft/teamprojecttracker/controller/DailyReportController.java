package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.constants.DefaultPageValues;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.CreateNewReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.DailyFilterResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.ViewDailyReportResponse;
import com.crocusoft.teamprojecttracker.exception.ReportNotFoundException;
import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.service.DailyReportService;
import com.crocusoft.teamprojecttracker.util.ExcelExport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/daily-report")
public class DailyReportController {
    private final DailyReportService dailyReportService;
    private final ExcelExport excelExport;

    @PutMapping("/edit-report{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DailyReport> editReport(@PathVariable("id") Long reportId, String text) {
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

    @GetMapping("download-report")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'USER')")
    public void downloadReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.xls";
        response.setHeader(headerKey, headerValue);
        excelExport.dailyReportDataToExcel(response);
    }
}