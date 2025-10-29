package com.zht.newclassmanager.pojo.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchCourseDTO implements Serializable {
    private Integer course_id;
    private String course_name;
    private String course_teacher;
}
