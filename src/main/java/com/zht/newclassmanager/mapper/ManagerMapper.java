package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.CourseSelected;
import com.zht.newclassmanager.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManagerMapper {
    Manager selectById(Integer manager_id);

    List<CourseSelected> selectCourseSelectedById(
            @Param("student_id")
            Integer student_id,
            @Param("course_id")
            Integer course_id);


    Integer removeSelectedCourse(
            @Param("student_id")
            Integer student_id,
            @Param("course_id")
            Integer course_id);

}
