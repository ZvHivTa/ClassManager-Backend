package com.zht.newclassmanager.pojo;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course implements Serializable {
    private Integer course_id;
    private String course_name;
    private String course_teacher;
    private String course_type;
    private Integer subject_id;
    private Integer course_grade;

    private String course_time;
    private String course_place;
    private Integer course_capacity;

    private Integer course_chosen_number;
}
