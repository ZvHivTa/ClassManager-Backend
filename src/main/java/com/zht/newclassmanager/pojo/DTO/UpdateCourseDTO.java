package com.zht.newclassmanager.pojo.DTO;

import lombok.*;

import java.io.Serializable;

//{"subject_id":"",
// "course_id":6000,
// "course_teacher":"��ǿ",
// "course_type":"2",
// "course_name":"ϰ��ƽ�й���ɫ�������˼����",
// "subject_name":"����",
// "index":0,
// "course_capacity":""}
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCourseDTO implements Serializable {

    private Integer course_id;
    private String course_name;
    private String course_teacher;
    private String course_type;
    private Integer subject_id;
    private Integer course_grade;
    private String course_time;
    private String course_place;
    private Integer course_capacity;


}
