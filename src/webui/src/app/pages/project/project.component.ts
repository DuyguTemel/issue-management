import {Component, OnInit, TemplateRef} from '@angular/core';
import {ProjectService} from "../../services/shared/project.service";
import {Page} from "../../common/page";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ConfirmationComponent} from "../../shared/confirmation/confirmation.component";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  modalRef: BsModalRef;
  projectForm: FormGroup;
  page = new Page();
  cols = [{prop: 'id', name: 'No'},
    {prop: 'projectName', name: 'Project Name', sortable: false},
    {prop: 'projectCode', name: 'Project Code', sortable: false}];
  rows = [];

  constructor(private projectService: ProjectService, private modalService: BsModalService, private formBuilder: FormBuilder) {

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
      'projectCode': [null, [Validators.required, Validators.minLength(2), Validators.maxLength(10)]]
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
        console.log(response);
      }
    )
    this.setPage(this.page);
    this.closeAndResetModal();
  }

  showDeleteConfirmation() {
    const model = this.modalService.show(ConfirmationComponent);
    (<ConfirmationComponent>model.content).showConfirmation('Header Content', 'Test Body Content');
    (<ConfirmationComponent>model.content).onClose.subscribe(result => {
        if (result === true) {
          console.log('Yes');
        } else if (result === false) {
          console.log('No');
        }
      });
  }
}
