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
//{"id":6057,"name":"大学英语II","teacher":"Johnson","time":"2025-09-01 10:00:00","place":"Lang-1","credit":3,"capacity":60,"type":4,"year":0,"collegeId":"9","subjectName":"英语"}
public class UpdateCourseDTO implements Serializable {

    private Integer id;
    private String name;
    private String teacher;
    private String type;
    private Integer collegeId;
    private Float credit;
    private String time;
    private String place;
    private Integer capacity;


}
