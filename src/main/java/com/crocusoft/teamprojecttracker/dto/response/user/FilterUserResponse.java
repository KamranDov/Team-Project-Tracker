package com.crocusoft.teamprojecttracker.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterUserResponse {

    @JsonProperty("users")
    List<UserFilterDto> users;

    @JsonProperty("total pages")
    Integer totalPages;

    @JsonProperty("total elements")
    Long totalElements;

    @JsonProperty("has next")
    Boolean hasNext;
}
