package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {
    private Integer student_id;
    private String student_name;
    private Integer student_grade;
    private String subject_name;
    private String college_name;
}
