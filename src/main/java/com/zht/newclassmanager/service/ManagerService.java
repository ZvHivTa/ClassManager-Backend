package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.*;
import java.util.List;

public interface ManagerService {
    Manager showManagerInfo(Integer manager_id);

    List<CourseSelected> showCourseSelected(Integer student_id,Integer course_id);

    Integer removeSelectedCourse(Integer student_id,Integer course_id);

    Integer chooseCourseForStudent(Integer student_id,Integer course_id);

    List<Course> searchCourse(Integer course_id, String course_name, String course_teacher);
    List<Subject> selectAllSubjects();

    Integer removeCourse(Integer course_id);

    Integer updateCourse(UpdateCourseDTO updateCourseDTO);

    Integer insertCourse(InsertCourseDTO insertCourseDTO);






}
