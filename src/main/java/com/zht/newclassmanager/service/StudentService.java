package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.Student;

import java.util.List;

public interface StudentService {

    Student showStudentInfo(Integer id);

    List<Course> showSuggestedCourses(Integer id);

    List<Course> showOptionalCourses();

    List<Course> showChosenCourses(Integer id);


    Integer chooseCourse(Integer student_id,Integer course_id);

    Integer removeChosenCourse(Integer student_id,Integer course_id);


}
