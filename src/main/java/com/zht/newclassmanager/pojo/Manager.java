package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Manager implements Serializable {
    private Integer manager_id;
    private String manager_name;
    private String college_name;
}
