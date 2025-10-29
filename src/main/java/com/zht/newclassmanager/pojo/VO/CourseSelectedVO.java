package com.zht.newclassmanager.pojo.VO;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseSelectedVO implements Serializable {
    private Integer course_id;
    private String course_name;
    private String course_teacher;

    private Integer student_id;
    private String student_name;
}
