package com.temelt.issuemanagement.service;

import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.util.TPage;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProjectService {

    ProjectDto save(ProjectDto issue);

    ProjectDto getById(Long id);

    ProjectDto getByProjectCode(String projectCode);

    List<ProjectDto> getByProjectCodeContains(String projectCode);

    TPage<ProjectDto> getAllPageable(Pageable pageable);

    Boolean delete(ProjectDto project);

    Boolean delete(Long id);

    ProjectDto update(Long id, ProjectDto projectDto);

    List<ProjectDto> getAll();
}
