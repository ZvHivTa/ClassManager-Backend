package com.zht.newclassmanager.pojo.DTO;

import lombok.*;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDTO implements Serializable {
    private Integer id;
    private String password;
}
