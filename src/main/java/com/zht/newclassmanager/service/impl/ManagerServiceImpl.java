package com.zht.newclassmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zht.newclassmanager.constant.MessageConstant;
import com.zht.newclassmanager.exception.AlreadySelectThisCourseException;
import com.zht.newclassmanager.exception.NoSuchSelectedRecordException;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchStudentDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;
import com.zht.newclassmanager.result.PageResult;
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
    public Admin showManagerInfo(Integer manager_id) {
        return managerMapper.selectById(manager_id);
    }

    @Override
    public List<Course> showCourseSelected(Integer student_id) {
        List<Course> courses = courseMapper.selectCourseSelectedByStudentId(student_id);
        return courses;
    }

    @Override
    public Integer removeSelectedCourse(Integer student_id, Integer course_id) {
        CourseSelected courseSelected = studentMapper.searchSelectedCourseRecord(student_id,course_id);
        if(courseSelected == null){
            throw new NoSuchSelectedRecordException(MessageConstant.NO_SUCH_SELECT_RECORD);
        }
        return managerMapper.removeSelectedCourse(student_id,course_id);
    }

    @Override
    public Integer chooseCourseForStudent(Integer student_id, Integer course_id) {
        CourseSelected courseSelected = studentMapper.searchSelectedCourseRecord(student_id,course_id);
        if(courseSelected != null){
            throw new AlreadySelectThisCourseException(MessageConstant.ALREADY_SELECT_THIS_COURSE);
        }
        return studentMapper.insertIntoCourseSelected(student_id,course_id);
    }

    @Override
    public PageResult searchCourse(CourseQueryDTO queryDTO) {

        PageHelper.startPage(queryDTO.getPage(),queryDTO.getPageSize());

        List<Course> courses = courseMapper.searchCoursesForManager(queryDTO.getCollegeId(),
                queryDTO.getTypeId(),
                queryDTO.getYear(),
                queryDTO.getKeyword());
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
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
        Course course = new Course();
        BeanUtils.copyProperties(updateCourseDTO,course);
        courseMapper.updateCourse(course);
        return result;
    }


    public Integer insertCourse(InsertCourseDTO insertCourseDTO){
        Course course = new Course();
        BeanUtils.copyProperties(insertCourseDTO,course);
        course.setSubjectId(course.getCollegeId()*100+1);
        courseMapper.insertCourse(course);
        return 0;
    }

    @Override
    public PageResult searchStudent(SearchStudentDTO searchStudentDTO) {
        PageHelper.startPage(searchStudentDTO.getPage(),searchStudentDTO.getPageSize());

        List<Student> students = studentMapper.searchStudent(
                searchStudentDTO.getCollegeId(),
                searchStudentDTO.getSubjectId(),
                searchStudentDTO.getKeyword()
        );

        PageInfo<Student> pageInfo = new PageInfo<>(students);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    public Course searchCourseById(Integer courseId) {
        Course course = courseMapper.getCourseById(courseId);
        return course;
    }
}
