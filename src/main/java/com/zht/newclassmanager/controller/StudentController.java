package com.zht.newclassmanager.controller;


import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@Tag(name = "学生")
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/personalinfo")
    @Operation(description = "获取学生个人信息")
    public Result<Student> personal_info_action() throws IOException {
        //TODO 从ThreadLocal中获取信息
        Integer account = BaseContext.getCurrentId();
        Student student = this.studentService.showStudentInfo(account);

        return Result.success(student);
    }

    @PostMapping("/choose_course")
    @Operation(description = "选课")
    public Result choose_course_action(Integer course_id){
        //TODO:从ThreadLocal中获取信息
        Integer account = BaseContext.getCurrentId();
        Integer result = studentService.chooseCourse(account,course_id);
        return Result.success();
    }

    @DeleteMapping("/remove_chosen_course")
    @Operation(description = "退选")
    public Result remove_chosen_course_action(Integer course_id){
        //TODO:从ThreadLocal中获取信息
        Integer user_id = BaseContext.getCurrentId();
        Integer result = studentService.removeChosenCourse(user_id,course_id);
        return Result.success();
    }

    @GetMapping("/optional")
    @Operation(description = "查看可选课程")
    public Result<List<Course>> show_optional_course_action(){
        List<Course> courses = studentService.showOptionalCourses();
        return Result.success(courses);
    }

    @GetMapping("/chosen")
    @Operation(description = "查看已选课程")
    public Result<List<Course>> show_chosen_course_action(Integer user_id){
        List<Course> courses = studentService.showChosenCourses(user_id);
        return Result.success(courses);
    }

    @GetMapping("/suggestion")
    @Operation(description = "查看推荐选课")
    public Result<List<Course>> show_suggestion_course_action(){
        //TODO:从ThreadLocal中获取信息
        Integer user_account = BaseContext.getCurrentId();
        List<Course> courses = studentService.showSuggestedCourses(user_account);
        return Result.success(courses);
    }


}
