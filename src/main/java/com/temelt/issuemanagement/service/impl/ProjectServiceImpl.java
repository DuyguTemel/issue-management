package com.temelt.issuemanagement.service.impl;

import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.entity.Project;
import com.temelt.issuemanagement.entity.User;
import com.temelt.issuemanagement.repository.ProjectRepository;
import com.temelt.issuemanagement.repository.UserRepository;
import com.temelt.issuemanagement.service.ProjectService;
import com.temelt.issuemanagement.util.TPage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectDto save(ProjectDto projectDto) {

        Project projectCheck = projectRepository.getByProjectCode(projectDto.getProjectCode());

        if (projectCheck != null)
            throw new IllegalArgumentException("Project Code already exist");
        Project p = modelMapper.map(projectDto, Project.class);
        User user=userRepository.getOne(projectDto.getManagerId());


        p = projectRepository.save(p);
        p.setManager(user);
        projectDto.setId(p.getId());
        return projectDto;
    }

    @Override
    public ProjectDto getById(Long id) {
        Project p = projectRepository.getOne(id);
        return modelMapper.map(p, ProjectDto.class);
    }

    @Override
    public ProjectDto getByProjectCode(String projectCode) {
        return null;
    }

    @Override
    public List<ProjectDto> getByProjectCodeContains(String projectCode) {
        return null;
    }

    @Override
    public TPage<ProjectDto> getAllPageable(Pageable pageable) {

        Page<Project> data = projectRepository.findAll(pageable);
        TPage<ProjectDto> response = new TPage<ProjectDto>();
        response.setStat(data, Arrays.asList(modelMapper.map(data.getContent(), ProjectDto[].class)));
        return response;
    }

    @Override
    public Boolean delete(ProjectDto project) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        projectRepository.deleteById(id);
        return true;
    }

    @Override
    public ProjectDto update(Long id, ProjectDto projectDto) {
        Project projectDb = projectRepository.getOne(id);
        if (projectDb == null)
            throw new IllegalArgumentException("Project does not exist ID:" + id);

        Project projectCheck = projectRepository.getByProjectCodeAndIdNot(projectDto.getProjectCode(), id);
        if (projectCheck != null)
            throw new IllegalArgumentException("Project Code already exist");

        projectDb.setProjectCode(projectDto.getProjectCode());
        projectDb.setProjectName(projectDto.getProjectName());

        projectRepository.save(projectDb);
        return modelMapper.map(projectDb, ProjectDto.class);

    }

    @Override
    public List<ProjectDto> getAll() {

        List<Project> data = projectRepository.findAll();
        return Arrays.asList( modelMapper.map(data, ProjectDto[].class));
    }
}
