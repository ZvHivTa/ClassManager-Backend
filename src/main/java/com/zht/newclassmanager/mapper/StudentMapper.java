package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.CourseSelected;
import com.zht.newclassmanager.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    //查找特定学生的基本信息
    Student selectById(Integer id);

    Integer insertIntoCourseSelected(Integer studentId, Integer courseId);

    Integer deleteFromCourseSelected(
                                     Integer studentId,

                                     Integer courseId);


    List<Student> searchStudent(Integer collegeId, Integer subjectId, String keyword);

    CourseSelected searchSelectedCourseRecord(Integer studentId, Integer courseId);
}
