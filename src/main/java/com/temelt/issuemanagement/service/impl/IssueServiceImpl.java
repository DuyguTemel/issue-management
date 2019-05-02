package com.temelt.issuemanagement.service.impl;

import com.temelt.issuemanagement.dto.*;
import com.temelt.issuemanagement.entity.Issue;
import com.temelt.issuemanagement.entity.IssueStatus;
import com.temelt.issuemanagement.entity.User;
import com.temelt.issuemanagement.repository.IssueHistoryRepository;
import com.temelt.issuemanagement.repository.IssueRepository;
import com.temelt.issuemanagement.repository.ProjectRepository;
import com.temelt.issuemanagement.repository.UserRepository;
import com.temelt.issuemanagement.service.IssueService;
import com.temelt.issuemanagement.util.TPage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueHistoryRepository issueHistoryRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final IssueHistoryServiceImpl issueHistoryService;
    private final ModelMapper modelMapper;

    public IssueServiceImpl(IssueRepository issueRepository, IssueHistoryRepository issueHistoryRepository, UserRepository userRepository, ProjectRepository projectRepository, IssueHistoryServiceImpl issueHistoryService, IssueHistoryServiceImpl issueHistoryService1, ModelMapper modelMapper) {
        this.issueRepository = issueRepository;
        this.issueHistoryRepository = issueHistoryRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.issueHistoryService = issueHistoryService1;
        this.modelMapper = modelMapper;
    }

    @Override
    public IssueDto save(IssueDto issue) {
        issue.setDate(new Date());
        issue.setIssueStatus(IssueStatus.OPEN);


        Issue issueEntity = modelMapper.map(issue, Issue.class);

        issueEntity.setProject(projectRepository.getOne(issue.getProjectId()));
        issueEntity = issueRepository.save(issueEntity);

        issue.setId(issueEntity.getId());
        return issue;
    }

    @Override
    public IssueDto getById(Long id) {
        return null;
//        return issueRepository.getOne(id);
    }



    @Override
    public List<IssueHistoryDto> getByIssueIdAndOrOrderById(Long id) {
        return Arrays.asList(modelMapper.map(issueHistoryRepository.getByIssueIdOrderById(id),IssueHistoryDto[].class));
    }


    @Override
    public TPage<IssueDto> getAllPageable(Pageable pageable) {

        Page<Issue> data = issueRepository.findAll(pageable);
        TPage page = new TPage<IssueDto>();
        IssueDto[] dtos = modelMapper.map(data.getContent(), IssueDto[].class);
        page.setStat(data, Arrays.asList(dtos));
        return page;
    }

    @Override
    public Boolean delete(Long id) {
        issueRepository.deleteById(id);
        return true;
    }

    public IssueDetailDto getByIdWithDetails(Long id) {
        Issue issue = issueRepository.getOne(id);
        IssueDetailDto detailDto = modelMapper.map(issue, IssueDetailDto.class);
        List<IssueHistoryDto> issueHistoryDtos = issueHistoryService.getByIssueId(issue.getId());
        detailDto.setIssueHistories(issueHistoryDtos);
        return detailDto;
    }

    @Override
    @Transactional
    public IssueDetailDto update(Long id, IssueUpdateDto issue) {
        Issue issueDb = issueRepository.getOne(id);
        User user = userRepository.getOne(issue.getAssignee_id());
        issueHistoryService.addHistory(id,issueDb);
        issueDb.setAssignee(user);
        issueDb.setDate(issue.getDate());
        issueDb.setDescription(issue.getDescription());
        issueDb.setDetails(issue.getDetails());
        issueDb.setIssueStatus(issue.getIssueStatus());
        issueDb.setProject(projectRepository.getOne(issue.getProject_id()));
        issueRepository.save(issueDb);
        return getByIdWithDetails(id);
    }
}
