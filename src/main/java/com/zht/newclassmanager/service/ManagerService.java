package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchCourseDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;

import java.util.List;

public interface ManagerService {
    Manager showManagerInfo(Integer manager_id);

    List<CourseSelectedVO> showCourseSelected(Integer student_id, Integer course_id);

    Integer removeSelectedCourse(Integer student_id,Integer course_id);

    Integer chooseCourseForStudent(Integer student_id,Integer course_id);

    List<Course> searchCourse(SearchCourseDTO searchCourseDTO);

    List<Subject> selectAllSubjects();

    Integer removeCourse(Integer course_id);

    Integer updateCourse(UpdateCourseDTO updateCourseDTO);

    Integer insertCourse(InsertCourseDTO insertCourseDTO);


}
