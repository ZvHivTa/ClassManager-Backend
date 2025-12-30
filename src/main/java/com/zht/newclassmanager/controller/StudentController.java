package com.zht.newclassmanager.controller;


import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.DTO.CourseSelectWithdrawDTO;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.result.PageResult;
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
    public Result<Student> personalInfo() throws IOException {

        Integer account = BaseContext.getCurrentId();
        Student student = this.studentService.showStudentInfo(account);

        return Result.success(student);
    }

    @PostMapping("/select")
    @Operation(description = "选课")
    public Result selectCourse(@RequestBody CourseSelectWithdrawDTO courseSelectDTO){

        Integer account = BaseContext.getCurrentId();
        Integer result = studentService.selectCourse(account,courseSelectDTO.getCourseId());
        return Result.success();
    }

    @PostMapping("/withdraw")
    @Operation(description = "退选")
    public Result withdrawCourse(@RequestBody CourseSelectWithdrawDTO courseWithdrawDTO){

        Integer user_id = BaseContext.getCurrentId();
        Integer result = studentService.removeChosenCourse(user_id,courseWithdrawDTO.getCourseId());
        return Result.success();
    }

    // 1. 超级查询接口 (原 /optional 的升级版)
    // 用于课程大厅、选课列表。支持分页、搜索、筛选
    @GetMapping("/search_courses")
    @Operation(summary = "课程库检索", description = "支持分页和多条件筛选")
    public Result<PageResult> searchCourses(CourseQueryDTO queryDTO) {
        // 这里 Spring MVC 会自动把 URL 参数映射到 queryDTO 对象中
        PageResult result = studentService.searchCourses(queryDTO);
        return Result.success(result);
    }

    // 2. 我的课程
    // 语义明确，专门查“我”的
    @GetMapping("/my_courses")
    @Operation(summary = "查看我的已选课程")
    public Result<List<Course>> getMyCourses() {
        Integer userId = BaseContext.getCurrentId();
        List<Course> courses = studentService.showChosenCourses(userId);
        return Result.success(courses);
    }

    // 3. 推荐课程
    // 这是一个特殊的业务场景，单独保留
    @GetMapping("/recommend")
    @Operation(summary = "获取推荐课程")
    public Result<List<Course>> getRecommendedCourses() {
        Integer userId = BaseContext.getCurrentId();
        List<Course> courses = studentService.getRecommendedCourses(userId);
        return Result.success(courses);
    }

}
