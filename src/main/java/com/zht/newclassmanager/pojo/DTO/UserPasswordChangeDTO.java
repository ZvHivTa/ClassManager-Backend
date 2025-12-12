package com.zht.newclassmanager.pojo.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordChangeDTO {
    private Integer id;
    private String oldPassword;
    private String newPassword;
}
