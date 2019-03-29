package com.temelt.issuemanagement.dto;

import com.temelt.issuemanagement.entity.Issue;
import com.temelt.issuemanagement.entity.IssueStatus;
import com.temelt.issuemanagement.entity.Project;
import com.temelt.issuemanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

//    private String username;
//
//    private String password;
//
    private String nameSurname;
//
//    private String email;

}
