package com.zht.newclassmanager.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class College implements Serializable {
    private Integer college_id;
    private String college_name;

}
