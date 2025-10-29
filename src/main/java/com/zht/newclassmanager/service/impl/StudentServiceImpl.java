package com.zht.newclassmanager.service.impl;

import com.zht.newclassmanager.mapper.CourseMapper;
import com.zht.newclassmanager.mapper.StudentMapper;
import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.Student;
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
    public List<Course> showSuggestedCourses(Integer id) {
        Student student = studentMapper.selectById(id);
        return courseMapper.selectForSuggestion(student);
    }

    @Override
    public List<Course> showOptionalCourses() {
        return courseMapper.selectForOptional(2);
    }

    @Override
    public List<Course> showChosenCourses(Integer id) {
        return courseMapper.selectForChosen(id);
    }

    @Override
    public Integer chooseCourse(Integer student_id, Integer course_id) {
        return studentMapper.insertIntoCourseSelected(student_id,course_id);
    }

    @Override
    public Integer removeChosenCourse(Integer student_id, Integer course_id) {
        return studentMapper.deleteFromCourseSelected(student_id,course_id);
    }
}
