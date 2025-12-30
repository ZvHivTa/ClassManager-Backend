package com.zht.newclassmanager.pojo.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//{"name":"高分子化学","teacher":"门捷列夫","time":"2025-09-03 14:00:00","place":"H-102","credit":2,"capacity":50,"type":2,"year":3,"collegeId":"8"}
public class InsertCourseDTO implements Serializable {
    private Integer id;
    private String name;
    private String teacher;
    private String time;
    private String place;

    private Float credit;
    private String type;
    private Integer year;
    private Integer collegeId;

    private Integer capacity;
}
