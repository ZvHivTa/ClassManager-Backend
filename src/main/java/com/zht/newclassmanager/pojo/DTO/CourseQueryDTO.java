package com.zht.newclassmanager.pojo.DTO;
import lombok.Data;


@Data
public class CourseQueryDTO {
    // 1. 分页参数
    // Spring 会自动把URL中的 &page=1&pageSize=50 映射进来
    private Integer page;

    private Integer pageSize;

    private String keyword;

    private Integer collegeId;

    private Integer typeId;

    private Integer year;

}