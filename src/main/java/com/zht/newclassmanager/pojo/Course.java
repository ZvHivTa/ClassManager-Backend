package com.zht.newclassmanager.pojo;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course implements Serializable {
    private Integer id;
    private String name;
    private String teacher;
    private String type;

    private Integer credit;

    private String time;
    private String place;
    private Integer capacity;
    private Integer chosenNumber;

    private String subjectName;
    private String collegeName;
}
