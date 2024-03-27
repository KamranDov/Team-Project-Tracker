package com.crocusoft.teamprojecttracker.controller;

import com.crocusoft.teamprojecttracker.dto.response.project.CreateProjectResponse;
import com.crocusoft.teamprojecttracker.dto.response.project.EditProjectAndRemoveUsersResponse;
import com.crocusoft.teamprojecttracker.dto.response.project.ProjectSearchResponse;
import com.crocusoft.teamprojecttracker.dto.response.project.ViewProjectAndUsersResponse;
import com.crocusoft.teamprojecttracker.exception.ProjectNotFoundException;
import com.crocusoft.teamprojecttracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<CreateProjectResponse> createProject(@RequestParam(value = "project name") String projectName,
                                                               @RequestParam(value = "employees") List<Long> employeesIds) {
        return new ResponseEntity<>(projectService.createProject(projectName, employeesIds), HttpStatus.CREATED);
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN', 'HEAD')")
    public ResponseEntity<ViewProjectAndUsersResponse> view(@PathVariable("id") Long id) throws ProjectNotFoundException {
        return new ResponseEntity<>(projectService.viewProjectAndUsersById(id), OK);
    }

    @PutMapping("/edit{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<EditProjectAndRemoveUsersResponse> edit(@PathVariable("id") Long projectId,
                                                                  @RequestParam(value = "new project name") String newProjectName,
                                                                  @RequestParam(value = "id of employees to be deleted") List<Long> oldEmployeesId) throws ProjectNotFoundException {
        EditProjectAndRemoveUsersResponse edited =
                projectService.editProjectAndUsersResponse(projectId, newProjectName, oldEmployeesId);
        return new ResponseEntity<>(edited, ACCEPTED);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN' , 'ADMIN')")
    public ResponseEntity<ProjectSearchResponse> searchProject(@RequestParam(value = "project name") String projectName) throws ProjectNotFoundException {
        return new ResponseEntity<>(projectService.searchByProjectName(projectName), OK);
    }
}
