package com.zht.newclassmanager.pojo.VO;

import com.zht.newclassmanager.enumration.Roletype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    private Integer id;
    private String token;
    private Roletype roletype;
    //    "info": {
//        "id": "20210001",
//                "name": "张三",
//                "avatar": "...",
//                "college": "信息工程学院", // 学生特有
//                "major": "计算机科学",     // 学生特有
//                "year": "大三"            // 学生特有
//    }
    private String name;
    private String avatar;
    private String college;
    private String major;
    private String year;

}
