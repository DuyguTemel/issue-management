package com.temelt.issuemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor

@NoArgsConstructor
public class ProjectDto {

    private Long id;
    @NotNull
    private String projectCode;
    @NotNull
    private String projectName;


}
