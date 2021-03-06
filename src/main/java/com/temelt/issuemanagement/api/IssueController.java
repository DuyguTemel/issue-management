package com.temelt.issuemanagement.api;

import com.temelt.issuemanagement.dto.*;
import com.temelt.issuemanagement.entity.IssueStatus;
import com.temelt.issuemanagement.service.impl.IssueServiceImpl;
import com.temelt.issuemanagement.util.ApiPaths;
import com.temelt.issuemanagement.util.TPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(ApiPaths.IssueCtrl.CTRL)
@CrossOrigin
public class IssueController {

    private final IssueServiceImpl issueService;

    public IssueController(IssueServiceImpl issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/pagination")
    public ResponseEntity<TPage<IssueDto>> getAllByPagination(Pageable pageable) {
        return ResponseEntity.ok(issueService.getAllPageable(pageable));
    }

    @GetMapping("/status")
    @ApiOperation(value = "Get All Issue Status", response = String.class,responseContainer = "List")
    public ResponseEntity<ArrayList<KeyValueObject>> getAll() {
        return ResponseEntity.ok(statusMap);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDto> getById(@PathVariable(value = "id", required = true) Long id) {
        IssueDto projectDto = issueService.getById(id);
        return ResponseEntity.ok(projectDto);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<IssueDetailDto> getByIdWithDetails(@PathVariable(value = "id", required = true) Long id) {
        IssueDetailDto issueDetailDto = issueService.getByIdWithDetails(id);
        return ResponseEntity.ok(issueDetailDto);
    }


    @PostMapping
    public ResponseEntity<IssueDto> createProject(@Valid @RequestBody IssueDto projectDto) {
        return ResponseEntity.ok(issueService.save(projectDto));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Operation", response = IssueDto.class)
    public ResponseEntity<IssueDetailDto> updateProject(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody IssueUpdateDto issue) {
        return ResponseEntity.ok(issueService.update(id,issue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable(value = "id", required = true) Long id) {
        return ResponseEntity.ok(issueService.delete(id));
    }



    public static ArrayList<KeyValueObject> statusMap = new ArrayList<>();

    static {
        statusMap.add(new KeyValueObject("Open", "OPEN"));
        statusMap.add(new KeyValueObject("Closed", "CLOSED"));
        statusMap.add(new KeyValueObject("In Review", "IN_REVIEW"));
        statusMap.add(new KeyValueObject("In Progress", "IN_PROGRESS"));
        statusMap.add(new KeyValueObject("Resolved", "RESOLVED"));
    }
}
