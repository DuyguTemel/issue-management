import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../../services/shared/project.service";
import {Page} from "../../common/page";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {

  page = new Page();
  rows = [];

  constructor(private projectService: ProjectService) {

  }



  ngOnInit() {
    this.setPage({ offset: 0 });

  }

  setPage(pageInfo){
    this.page.number = pageInfo.offset;
    this.projectService.getAll(this.page).subscribe(pagedData => {
      this.page.size = pagedData.size;
      this.page.number = pagedData.number;
      this.rows = pagedData.content;
    });
  }

}
