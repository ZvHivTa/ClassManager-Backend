package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseSelected implements Serializable {
    private Integer student_id;
    private Integer course_id;
}
