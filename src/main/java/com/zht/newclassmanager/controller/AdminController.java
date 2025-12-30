package com.zht.newclassmanager.controller;


import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.DTO.CourseQueryDTO;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchStudentDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;
import com.zht.newclassmanager.result.PageResult;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.ManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "管理员")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/info")
    @Operation(description = "获取管理员个人信息")
    public Result<Admin> manager_info_action(){
        //TODO 从ThreadLocal中获取信息
        Integer currentId = BaseContext.getCurrentId();
        Admin manager = managerService.showManagerInfo(currentId);
        return Result.success(manager);
    }



    @GetMapping("/load_subject")
    @Operation(description = "查询学科")
    public Result<List<Subject>> load_subject_action(){
        List<Subject> subjects = managerService.selectAllSubjects();
        return Result.success(subjects);
    }

    // http://localhost/api/admin/courses/search?page=1&pageSize=10&keyword=man&collegeId=info&typeId=1&year=1
    @GetMapping("/course/search")
    @Operation(description = "查询课程信息")
    public Result<PageResult> searchCourse(CourseQueryDTO courseQueryDTO){

        PageResult courses =  managerService.searchCourse(courseQueryDTO);

        return Result.success(courses);
    }

    @GetMapping("/course/search/{courseId}")
    @Operation(description = "根据ID查询课程信息")
    public Result<Course> searchCourseById(@PathVariable Integer courseId){

        Course course =  managerService.searchCourseById(courseId);

        return Result.success(course);
    }


    @PutMapping("/course/update")
    @Operation(description = "更新课程信息")
    public Result updateCourse(@RequestBody UpdateCourseDTO updateCourseDTO){
        Integer result = managerService.updateCourse(updateCourseDTO);
        return Result.success();
    }

    @PostMapping("/course/insert")
    @Operation(description = "新增课程")
    public Result insertCourse(@RequestBody InsertCourseDTO insertCourseDTO){
        Integer result = managerService.insertCourse(insertCourseDTO);
        return Result.success();
    }

    @DeleteMapping("/course/remove")
    @Operation(description = "删除课程")
    public Result removeCourse(Integer course_id){
        Integer result =  managerService.removeCourse(course_id);
        return Result.success();
    }

    @PostMapping("/student/withdraw")
    @Operation(description = "删除特定选课记录")
    public Result remove_chosen_course_action(@RequestBody CourseSelected courseSelected){
        Integer result =  managerService.removeSelectedCourse(
                courseSelected.getStudentId(),
                courseSelected.getCourseId());
        return Result.success();
    }

    @PostMapping("/student/select")
    @Operation(description = "增加特定选课记录")
    public Result select_course_action(@RequestBody CourseSelected courseSelected){
        Integer result = managerService.chooseCourseForStudent(
                courseSelected.getStudentId(),
                courseSelected.getCourseId()
        );
        return Result.success();
    }

    @GetMapping("/student/selectedCourse/{studentId}")
    @Operation(description = "查看学生已选课程")
    public Result<List<Course>> search_course_selected_info_action(@PathVariable Integer studentId){

        List<Course> list= managerService.showCourseSelected(studentId);
        return Result.success(list);
    }

    @GetMapping("/student/search")
    @Operation(description = "查找学生")
    public Result<PageResult> searchStudent(SearchStudentDTO searchStudentDTO){
        PageResult pageResult = managerService.searchStudent(searchStudentDTO);
        return Result.success(pageResult);
    }
}
