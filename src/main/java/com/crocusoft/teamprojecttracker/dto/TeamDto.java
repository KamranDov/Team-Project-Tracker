package com.crocusoft.teamprojecttracker.dto;

import com.crocusoft.teamprojecttracker.model.Team;

import java.io.Serializable;

/**
 * DTO for {@link Team}
 */
public record TeamDto(String name) implements Serializable {
}