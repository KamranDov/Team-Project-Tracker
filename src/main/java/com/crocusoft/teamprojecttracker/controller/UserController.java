package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.constants.DefaultPageValues;
import com.crocusoft.teamprojecttracker.dto.request.UserRequest;
import com.crocusoft.teamprojecttracker.dto.response.dailyReport.CreateNewReportResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.CreateAndEditUserResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.FilterUserResponse;
import com.crocusoft.teamprojecttracker.dto.response.user.ViewUserResponse;
import com.crocusoft.teamprojecttracker.enums.UserActionStatus;
import com.crocusoft.teamprojecttracker.exception.*;
import com.crocusoft.teamprojecttracker.model.DailyReport;
import com.crocusoft.teamprojecttracker.service.AuthService;
import com.crocusoft.teamprojecttracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PutMapping("/edit{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<CreateAndEditUserResponse> edit(@PathVariable("id") Long id,
                                                          @RequestBody UserRequest userRequest,
                                                          @RequestParam(value = "User status") UserActionStatus userActionStatus)
            throws RoleNotFoundException, TeamNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(userService.editUser(id, userRequest, userActionStatus), OK);
    }

    @GetMapping("/view{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'HEAD')")
    public ResponseEntity<ViewUserResponse> view(@PathVariable("id") Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.viewUserById(id), OK);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'HEAD')")
    public ResponseEntity<FilterUserResponse> filterUsers(
            @RequestParam(value = "page number", defaultValue = DefaultPageValues.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "page count", defaultValue = DefaultPageValues.PAGE_COUNT) Integer pageCount,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "team_id", required = false) List<Long> teamId,
            @RequestParam(value = "project_id", required = false) List<Long> projectId) {

        FilterUserResponse response = userService.filterUser(pageNumber, pageCount, name, surname, teamId, projectId);
        return ResponseEntity.ok(response);
    }

}
