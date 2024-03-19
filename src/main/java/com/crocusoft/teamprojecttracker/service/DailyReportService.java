package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.response.dailyReport.CreateNewReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.DailyFilterResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.ViewDailyReportResponse;
import com.crocusoft.teamprojecttracker.exception.ReportCreationTimeExpiredException;
import com.crocusoft.teamprojecttracker.exception.ReportNotFoundException;
import com.crocusoft.teamprojecttracker.exception.RolePermissionException;
import com.crocusoft.teamprojecttracker.exception.UserNotFoundException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.model.Project;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.DailyReportRepository;
import com.crocusoft.teamprojecttracker.repository.ProjectRepository;
import com.crocusoft.teamprojecttracker.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class DailyReportService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final DailyReportRepository dailyReportRepository;
    private final Convert convert;

    public CreateNewReportResponse newReport(Long employeeId, String text, String projectNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("USER"))) {
            log.error("Unauthorized attempt to create a new report by a non-USER user");
            throw new RolePermissionException("No permission");
        }
        User authenticatedUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + authentication.getName()));

        if (!authenticatedUser.getId().equals(employeeId)) {
            log.error("Unauthorized attempt to create a new report for another user");
            throw new RolePermissionException("No permission to create report for another user");
        }
        Project byName = projectRepository.findByName(projectNames);
            User user = userRepository.findById(employeeId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + employeeId));

        DailyReport dailyReport = DailyReport.builder()
                    .employee(user)
                    .project(byName)
                    .description(text)
                    .build();
            return convert.dailyReportToCreateNewReportResponse(dailyReportRepository.save(dailyReport));
        }

    public DailyReport updateReport(Long reportId, String text){
        Optional<DailyReport> dailyReportOptional = dailyReportRepository.findById(reportId);
        if (dailyReportOptional.isPresent()) {
            DailyReport existingReport = dailyReportOptional.get();

            LocalDateTime createdAtEndOfDay = existingReport.getCreatedAt().atTime(LocalTime.MAX);
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(createdAtEndOfDay)) {
                existingReport.setDescription(text);
                dailyReportRepository.save(existingReport);
                return existingReport;
            } else throw new ReportCreationTimeExpiredException("Report creation time has expired");
        }
        throw new UserNotFoundException("Report not found with ID: " + reportId);
    }

    public ViewDailyReportResponse viewReport(Long reportId){
        Optional<DailyReport> dailyReportOptional = dailyReportRepository.findById(reportId);
        if (dailyReportOptional.isPresent()) {
            return convert.dailyReportToViewDailyReportResponse(dailyReportOptional.get());
        } else throw new ReportNotFoundException("Report not found with ID: " + reportId);
    }

    public DailyFilterResponse filterReport(Integer pageNumber, Integer pageCount, List<Long> employeeIds) {
        Page<DailyReport> dailyReportPage = dailyReportRepository.findAll(PageRequest.of(pageNumber, pageCount));

        List<DailyReport> filteredReports = dailyReportPage
                .getContent()
                .stream()
                .filter(report -> employeeIds.contains(report.getEmployee().getId()))
                .toList();

        return new DailyFilterResponse(
                filteredReports.stream().map(employeeId -> employeeId.getEmployee().getId()).collect(Collectors.toList()),
                filteredReports.stream().map(employeeProjectName -> employeeProjectName.getProject().getName()).toList(),
                filteredReports.stream().map(DailyReport::getCreatedAt).toList(),
                dailyReportPage.getTotalPages(),
                dailyReportPage.getTotalElements(),
                dailyReportPage.hasNext());
    }
}
