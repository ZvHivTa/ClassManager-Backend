package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchStudentDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;
import com.zht.newclassmanager.result.PageResult;

import java.util.List;

public interface ManagerService {
    Admin showManagerInfo(Integer manager_id);

    List<Course> showCourseSelected(Integer student_id);

    Integer removeSelectedCourse(Integer student_id,Integer course_id);

    Integer chooseCourseForStudent(Integer student_id,Integer course_id);

    PageResult searchCourse(CourseQueryDTO courseQueryDTO);

    List<Subject> selectAllSubjects();

    Integer removeCourse(Integer course_id);

    Integer updateCourse(UpdateCourseDTO updateCourseDTO);

    Integer insertCourse(InsertCourseDTO insertCourseDTO);


    PageResult searchStudent(SearchStudentDTO searchStudentDTO);

    Course searchCourseById(Integer courseId);
}
