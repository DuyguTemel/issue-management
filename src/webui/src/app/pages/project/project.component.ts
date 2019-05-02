import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ProjectService} from "../../services/shared/project.service";
import {Page} from "../../common/page";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmationComponent} from "../../shared/confirmation/confirmation.component";
import {UserService} from "../../services/shared/user.service";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  modalRef: BsModalRef;
  projectForm: FormGroup;
  @ViewChild('tplProjectDeleteCell') tplProjectDeleteCell: TemplateRef<any>;

  page = new Page();
  cols = [];
  rows = [];
  managerOptions = [];

  constructor(private projectService: ProjectService, private modalService: BsModalService, private formBuilder: FormBuilder,
              private userService: UserService) {

  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  get f() {
    return this.projectForm.controls;
  }

  ngOnInit(): void {

    this.setPage({offset: 0});
    this.projectForm = this.formBuilder.group({
      'projectName': [null, [Validators.required, Validators.minLength(4)]],
      'projectCode': [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
      'managerId': [null, [Validators.required]]
    });

    this.cols = [{prop: 'id', name: 'No'},
      {prop: 'projectName', name: 'Project Name', sortable: false},
      {prop: 'projectCode', name: 'Project Code', sortable: false},
      {prop: 'manager.nameSurname', name: 'Manager', sortable: false},
      {prop: 'id', name: 'Actions', cellTemplate: this.tplProjectDeleteCell, sortable: false}];
    this.userService.getAll().subscribe(result => {
      console.log(result);
      this.managerOptions = result;
    });
  }

  closeAndResetModal() {
    this.projectForm.reset();
    this.modalRef.hide();
  }

  setPage(pageInfo) {
    this.page.page = pageInfo.offset;
    this.projectService.getAll(this.page).subscribe(pagedData => {
      this.page.size = pagedData.size;
      this.page.page = pagedData.number;
      this.page.totalElements = pagedData.totalElements;
      this.rows = pagedData.content;
    });
  }

  saveProject() {
    if (!this.projectForm.valid) {
      return;
    }
    this.projectService.createProject(this.projectForm.value).subscribe(
      response => {
        this.setPage({offset: 0});
        this.closeAndResetModal();
        console.log(response);
      }
    );
  }

  showProjectDeleteConfirmation(value) {
    const model = this.modalService.show(ConfirmationComponent);
    (<ConfirmationComponent>model.content).showConfirmation('Delete Confirmation', 'Are you sure for delete project?');
    (<ConfirmationComponent>model.content).onClose.subscribe(result => {
      if (result === true) {
        this.projectService.delete(value).subscribe(response => {
          if (response === true) {
            this.setPage({offset: 0});
          }
        });
      } else if (result === false) {
      }
    });
  }
}
