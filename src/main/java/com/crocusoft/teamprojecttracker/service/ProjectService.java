package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.response.project.*;
import com.crocusoft.teamprojecttracker.exception.ProjectNotFoundException;
import com.crocusoft.teamprojecttracker.mapper.Convert;
import com.crocusoft.teamprojecttracker.model.Project;
import com.crocusoft.teamprojecttracker.model.User;
import com.crocusoft.teamprojecttracker.repository.ProjectRepository;
import com.crocusoft.teamprojecttracker.repository.TeamRepository;
import com.crocusoft.teamprojecttracker.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final Convert convert;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public CreateProjectResponse createProject(String projectName, List<Long> employeesIds) {
        List<User> userList = userRepository.findByIdIn(employeesIds);
        Project newProject = Project.builder()
                .name(projectName)
                .employees(userList)
                .build();
        return convert.projectToProjectCreateResponse(projectRepository.save(newProject));
    }

    public ViewProjectAndUsersResponse viewProjectAndUsersById(Long id) throws ProjectNotFoundException {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ViewProjectAndUsersResponse viewProjectAndUsersResponse = convert.projectToProjectViewResponse(project);
            return viewProjectAndUsersResponse;
        } else throw new ProjectNotFoundException("No project found with the given ID" + id);
    }

    public EditProjectAndRemoveUsersResponse editProjectAndUsersResponse(Long projectId,
                                                                         String newProjectName,
                                                                         List<Long> oldEmployeesId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project existingProject = projectOptional.get();
            existingProject.setName(newProjectName);

            List<User> updateUsers = new ArrayList<>();
            List<Long> removedUserId = new ArrayList<>();

            for (Long userId : oldEmployeesId) {
                Optional<User> userOptional = userRepository.findById(userId);
                userOptional.ifPresent(user -> {
                    updateUsers.add(user);
                    removedUserId.add(user.getId());
                });
            }
            existingProject.getEmployees().removeAll(updateUsers);
            projectRepository.save(existingProject);

            return new EditProjectAndRemoveUsersResponse("Project name updated successfully",
                    existingProject.getName(),
                    removedUserId);
        } else {
            throw new ProjectNotFoundException("No project found with the given id");
        }
    }

    public ProjectSearchResponse searchByProjectName(String projectName) {
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            throw new ProjectNotFoundException("Project not found with the given name" + projectName);
        }
        List<User> usersInProject = userRepository.findAllByProjectsId(project.getId());
        List<ProjectFilterResponseItem> projectFilterResponseItems = new ArrayList<>();

        for (User user : usersInProject) {
            ProjectFilterResponseItem responseItem = ProjectFilterResponseItem.builder()
                    .employeeId(user.getId())
                    .employeeName(user.getName())
                    .employeeSurname(user.getSurname())
                    .employeeEmail(user.getEmail())
                    .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                    .teamId(user.getTeam().getId())
                    .teamName(user.getTeam().getName())
                    .build();
            projectFilterResponseItems.add(responseItem);
        }
        return new ProjectSearchResponse(project.getId(), project.getName(), projectFilterResponseItems);
    }
}
