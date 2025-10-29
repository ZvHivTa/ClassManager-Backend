package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface StudentMapper {
    //查找特定学生的基本信息
    Student selectById(Integer id);

    Integer insertIntoCourseSelected(
            @Param("student_id")
            Integer student_id,
            @Param("course_id")
            Integer course_id);

    Integer deleteFromCourseSelected(@Param("student_id")
                                     Integer student_id,
                                     @Param("course_id")
                                     Integer course_id);



}
