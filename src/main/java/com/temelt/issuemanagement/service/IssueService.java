package com.temelt.issuemanagement.service;

import com.temelt.issuemanagement.dto.IssueDto;
import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.util.TPage;
import org.springframework.data.domain.Pageable;


public interface IssueService {

    IssueDto save(IssueDto issue);

    IssueDto getById(Long id);

    TPage<IssueDto> getAllPageable(Pageable pageable);

    IssueDto update(Long id, ProjectDto projectDto);

    Boolean delete(Long id);
}
