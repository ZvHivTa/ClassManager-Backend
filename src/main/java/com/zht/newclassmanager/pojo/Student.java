package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {
    private Integer id;
    private String name;
    private Integer year;
    private Integer subjectId;
    private Integer collegeId;
    private String subjectName;
    private String collegeName;
}
