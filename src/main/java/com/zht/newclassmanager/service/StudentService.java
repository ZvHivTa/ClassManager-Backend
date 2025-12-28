package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.result.PageResult;

import java.util.List;

public interface StudentService {

    Student showStudentInfo(Integer id);

    List<Course> showSuggestedCourses(Integer id);

    List<Course> showChosenCourses(Integer id);

    Integer selectCourse(Integer student_id,Integer course_id);

    Integer removeChosenCourse(Integer student_id,Integer course_id);


    PageResult searchCourses(CourseQueryDTO queryDTO);
}
