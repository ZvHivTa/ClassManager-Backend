package com.zht.newclassmanager.service.impl;

import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchCourseDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;
import com.zht.newclassmanager.service.ManagerService;
import com.zht.newclassmanager.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    StudentMapper studentMapper;
    @Override
    public Manager showManagerInfo(Integer manager_id) {
        return managerMapper.selectById(manager_id);
    }

    @Override
    public List<CourseSelectedVO> showCourseSelected(Integer student_id, Integer course_id) {
        List<CourseSelected> relations = managerMapper.selectCourseSelectedById(student_id, course_id);
        List<CourseSelectedVO> result = new ArrayList<>();
        relations.forEach(item -> {
            CourseSelectedVO courseSelectedVO = new CourseSelectedVO();
            BeanUtils.copyProperties(courseSelectedVO,item);
            result.add(courseSelectedVO);
        });
        return result;
    }

    @Override
    public Integer removeSelectedCourse(Integer student_id, Integer course_id) {
        return managerMapper.removeSelectedCourse(student_id,course_id);
    }

    @Override
    public Integer chooseCourseForStudent(Integer student_id, Integer course_id) {
        return studentMapper.insertIntoCourseSelected(student_id,course_id);
    }

    @Override
    public List<Course> searchCourse(SearchCourseDTO searchCourseDTO) {
        Integer course_id = searchCourseDTO.getCourse_id();
        String course_name = searchCourseDTO.getCourse_name();
        String course_teacher = searchCourseDTO.getCourse_teacher();

        return courseMapper.selectForManager(course_id,course_name,course_teacher);
    }

    @Override
    public List<Subject> selectAllSubjects() {
        return subjectMapper.selectAllSubjects();
    }

    @Override
    public Integer removeCourse(Integer course_id) {
        return courseMapper.removeCourse(course_id);
    }


    public Integer updateCourse(UpdateCourseDTO updateCourseDTO) {
        Integer result = 0;
        Integer num = null;
        Course course = new Course();
        BeanUtils.copyProperties(updateCourseDTO,course);
        num = courseMapper.selectFromCourseArrangment(course.getCourse_id());
        if(num==null){
            courseMapper.updateCourse(course);
            result = courseMapper.insertIntoCourseArrangment(course);
        }else{
            courseMapper.updateCourse(course);
            result = courseMapper.updateCourseArrangment(course);
        }
        return result;
    }


    public Integer insertCourse(InsertCourseDTO insertCourseDTO){
        Course course = new Course();
        BeanUtils.copyProperties(insertCourseDTO,course);
        Integer num = null;
        List<Course> courses = courseMapper.selectForManager(course.getCourse_id(),null,null);
        num = courses.size();
        if(num==0){
            courseMapper.insertCourse(course);
            num = courseMapper.insertIntoCourseArrangment(course);
        }
        return num;
    }
}
