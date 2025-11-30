package com.zht.newclassmanager.pojo;

import com.zht.newclassmanager.enumration.Roletype;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private Integer id;
    private String password;
    private Roletype roletype;
}
