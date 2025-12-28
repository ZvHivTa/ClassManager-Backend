package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> selectForSuggestion(Student student);

    List<Course> selectForChosen(Integer student_id);

    List<Course> selectForManager(
            @Param("course_id")
            Integer course_id,
            @Param("course_name")
            String course_name,
            @Param("course_teacher")
            String course_teacher);

    Integer removeCourse(@Param("course_id") Integer course_id);

    Integer updateCourse(Course course);

    Integer insertCourse(Course course);


    Integer selectFromCourseArrangment(Integer course_id);

    Integer insertIntoCourseArrangment(Course course);

    Integer updateCourseArrangment(Course course);


    List<Course> searchCourses(Integer collegeId,
                               Integer typeId,
                               Integer year,
                               String keyword);

    Course getCourseById(Integer courseId);

}
