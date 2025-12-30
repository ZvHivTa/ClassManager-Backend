package com.zht.newclassmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zht.newclassmanager.constant.MessageConstant;
import com.zht.newclassmanager.exception.FailedToSelectCourseException;
import com.zht.newclassmanager.exception.FailedToWithdrawCourseException;
import com.zht.newclassmanager.exception.PasswordErrorException;
import com.zht.newclassmanager.mapper.CourseMapper;
import com.zht.newclassmanager.mapper.StudentMapper;
import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.result.PageResult;
import com.zht.newclassmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Student showStudentInfo(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Course> getRecommendedCourses(Integer id) {
        return courseMapper.getRecommendedCourses(id);
    }


    @Override
    public List<Course> showChosenCourses(Integer id) {
        return courseMapper.selectForChosen(id);
    }

    @Override
    public Integer selectCourse(Integer student_id, Integer course_id) {
        Course course = courseMapper.getCourseById(course_id);
        if(course == null){
            throw new FailedToSelectCourseException(MessageConstant.COURSE_NOT_EXIST);
        }
        if(course.getCapacity() <= course.getChosenNumber()){
            throw new FailedToSelectCourseException(MessageConstant.COURSE_CAPACITY_IS_FULL);
        }
        return studentMapper.insertIntoCourseSelected(student_id, course_id);
    }

    @Override
    public Integer removeChosenCourse(Integer student_id, Integer course_id) {
        Course course = courseMapper.getCourseById(course_id);
        if(course == null){
            throw new FailedToWithdrawCourseException(MessageConstant.COURSE_NOT_EXIST);
        }
        return studentMapper.deleteFromCourseSelected(student_id,course_id);
    }

    @Override
    public PageResult searchCourses(CourseQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(),queryDTO.getPageSize());

        List<Course> courses = courseMapper.searchCourses(queryDTO.getCollegeId(),
                queryDTO.getTypeId(),
                queryDTO.getYear(),
                queryDTO.getKeyword());
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        PageResult pageResult = new PageResult();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }
}
