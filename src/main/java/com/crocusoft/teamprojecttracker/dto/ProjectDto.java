package com.crocusoft.teamprojecttracker.dto;

import com.crocusoft.teamprojecttracker.model.Project;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Project}
 */

public record ProjectDto(String name) implements Serializable {
}