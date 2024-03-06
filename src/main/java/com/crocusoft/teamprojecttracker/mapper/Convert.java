package com.crocusoft.teamprojecttracker.mapper;

import com.crocusoft.teamprojecttracker.dto.response.dailyReport.CreateNewReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.ViewDailyReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.project.CreateProjectResponse;
import com.crocusoft.teamprojecttracker.dto.response.project.ViewProjectAndUsersResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.CreateTeamResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.TeamByUsersResponse;
import com.crocusoft.teamprojecttracker.dto.response.team.ViewTeamAllUsersResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.CreateAndEditUserResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.ViewUserResponse;
import com.crocusoft.teamprojecttracker.model.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Convert {
    public CreateAndEditUserResponse userToUserResponse(User user) {
        return new CreateAndEditUserResponse(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
//                user.getAuthorities().stream()
//                        .filter(authority -> authority instanceof Role)
//                        .map(authority -> ((Role) authority).getName())
//                        .toList(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()),
                user.getTeam().getName());
    }

    public CreateProjectResponse projectToProjectCreateResponse(Project project) {
        return new CreateProjectResponse(project.getName(), project.getEmployees().stream().map(User::getId).toList());
    }

    public CreateTeamResponse teamCreate(Team team) {
        return new CreateTeamResponse(team.getName());
    }

    public CreateNewReportResponse dailyReportToCreateNewReportResponse(DailyReport dailyReport) {
        return new CreateNewReportResponse(
                dailyReport.getEmployee().getId(),
                dailyReport.getProject().getId(),
                dailyReport.getDescription()
        );

    }

    public ViewUserResponse userToUserViewResponse(User user) {
        return new ViewUserResponse(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
//                user.getAuthorities().stream()
//                        .filter(authority -> authority instanceof Role)
//                        .map(authority -> ((Role) authority).getName())
//                        .collect(Collectors.toSet()),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining()),
                user.getTeam().getName(),
                user.getProjects().stream().map(Project::getName).toList()
        );
    }

    public ViewProjectAndUsersResponse projectToProjectViewResponse(Project project) {
        return new ViewProjectAndUsersResponse(
                project.getName(),
                project.getEmployees().stream().map(User::getId).toList()
        );
    }

    public ViewTeamAllUsersResponse teamToTeamViewAllUsersResponse(Team team) {
        List<TeamByUsersResponse> userResponses = team.getEmployeeList().stream()
                .map(user -> new TeamByUsersResponse(user.getName(), user.getSurname()))
                .collect(Collectors.toList());
        return new ViewTeamAllUsersResponse(team.getName(), userResponses);
    }

    public ViewDailyReportResponse dailyReportToViewDailyReportResponse(DailyReport dailyReport) {
        return new ViewDailyReportResponse(
                dailyReport.getId(),
                dailyReport.getDescription(),
                dailyReport.getCreatedAt(),
                dailyReport.getEmployee().getName(),
                dailyReport.getEmployee().getSurname(),
                dailyReport.getProject().getId());
    }

//    public FilterUserResponse userToFilterUserResponse(User user) {
//        return new FilterUserResponse(
//                user.getId(),
//                user.getName(),
//                user.getSurname(),
//                user.getEmail(),
//                user.getAuthorities().stream()
//                        .filter(authority -> authority instanceof Role)
//                        .map(authority -> ((Role) authority).getName())
//                        .toList().toString(),
//                user.getTeam().getId(),
//                user.getProjects().stream().map(Project::getId).toList());
//    }
}