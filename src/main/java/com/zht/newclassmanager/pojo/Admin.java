package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin implements Serializable {
    private Integer id;
    private String name;
    private String college;
}
