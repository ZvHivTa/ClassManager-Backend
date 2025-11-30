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
    private String major;
    private String college;
}
