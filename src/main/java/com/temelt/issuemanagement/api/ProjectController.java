package com.temelt.issuemanagement.api;
import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.service.impl.ProjectServiceImpl;
import com.temelt.issuemanagement.util.ApiPaths;
import com.temelt.issuemanagement.util.TPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.ProjectCtrl.CTRL)
@Api(value = ApiPaths.ProjectCtrl.CTRL, description = "Project APIS")
@Slf4j
@CrossOrigin
public class ProjectController {

    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/pagination")
    @ApiOperation(value = "Get by Pagination Operation", response = ProjectDto.class)
    public ResponseEntity<TPage<ProjectDto>> getAllByPagination(Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllPageable(pageable));
    }

    @GetMapping()
    @ApiOperation(value = "Get All Operation", response = ProjectDto.class,responseContainer = "List")
    public ResponseEntity<List<ProjectDto>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get by id Operation", response = ProjectDto.class)
    public ResponseEntity<ProjectDto> getById(@PathVariable(value = "id", required = true) Long id) {

        log.info("ProjectController-getById- PARAM " + id);
        log.debug("ProjectController-getById- PARAM " + id);

        ProjectDto projectDto = projectService.getById(id);
        return ResponseEntity.ok(projectDto);
    }

    @PostMapping
    @ApiOperation(value = "Create Operation", response = ProjectDto.class)
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.save(projectDto));
    }

    //    @RequestMapping(path = "/update",method = RequestMethod.PUT)
    @PutMapping("/{id}")
    @ApiOperation(value = "Update Operation", response = ProjectDto.class)
    public ResponseEntity<ProjectDto> updateProject(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.update(id, projectDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Operation", response = Boolean.class)
    public ResponseEntity<Boolean> deleteProject(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(projectService.delete(id));
    }

}