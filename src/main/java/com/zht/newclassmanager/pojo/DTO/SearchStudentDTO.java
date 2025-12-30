package com.zht.newclassmanager.pojo.DTO;

import lombok.Data;

@Data
public class SearchStudentDTO {

    Integer collegeId;
    Integer subjectId;

    String keyword;

    Integer page;
    Integer pageSize;
}
