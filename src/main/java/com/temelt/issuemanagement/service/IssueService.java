package com.temelt.issuemanagement.service;

import com.temelt.issuemanagement.dto.IssueDetailDto;
import com.temelt.issuemanagement.dto.IssueDto;
import com.temelt.issuemanagement.dto.IssueHistoryDto;
import com.temelt.issuemanagement.dto.IssueUpdateDto;
import com.temelt.issuemanagement.util.TPage;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IssueService {

    IssueDto save(IssueDto issue);

    IssueDto getById(Long id);

    TPage<IssueDto> getAllPageable(Pageable pageable);

    IssueDetailDto update(Long id, IssueUpdateDto projectDto);

    List<IssueHistoryDto> getByIssueIdAndOrOrderById(Long id);

    Boolean delete(Long id);
}
