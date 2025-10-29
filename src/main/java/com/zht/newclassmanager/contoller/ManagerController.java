package com.zht.newclassmanager.contoller;


import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.SearchCourseDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.pojo.VO.CourseSelectedVO;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.ManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "管理员")
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/info")
    @Operation(description = "获取管理员个人信息")
    public Result<Manager> manager_info_action(){
        //TODO 从ThreadLocal中获取信息
        Integer currentId = BaseContext.getCurrentId();
        Manager manager = managerService.showManagerInfo(currentId);
        return Result.success(manager);
    }

    @GetMapping("/course_selected")
    @Operation(description = "查看学生已选课程")
    public Result<List<CourseSelectedVO>> search_course_selected_info_action(CourseSelected courseSelected){

        List<CourseSelectedVO> list= managerService.showCourseSelected(courseSelected.getStudent_id(),courseSelected.getCourse_id());
        return Result.success(list);
    }


    @GetMapping("/load_subject")
    @Operation(description = "查询学科")
    public Result<List<Subject>> load_subject_action(){
        List<Subject> subjects = managerService.selectAllSubjects();
        return Result.success(subjects);
    }


    @GetMapping("/search_course")
    @Operation(description = "查询课程信息")
    public Result<List<Course>> search_course_action(SearchCourseDTO searchCourseDTO){

        List<Course> courses =  managerService.searchCourse(searchCourseDTO);
        return Result.success(courses);
    }

    @PostMapping("/select_course")
    @Operation(description = "增加特定选课记录")
    public Result select_course_action(CourseSelected courseSelected){
        Integer result = managerService.chooseCourseForStudent(
                courseSelected.getStudent_id(),
                courseSelected.getCourse_id()
        );

        return Result.success();
    }

    @PutMapping("/update_course")
    @Operation(description = "更新课程信息")
    public Result update_course_action(UpdateCourseDTO updateCourseDTO){

        Integer result = managerService.updateCourse(updateCourseDTO);
        return Result.success();
    }

    @PutMapping("/insert_course")
    @Operation(description = "新增课程")
    public Result insert_course_action(InsertCourseDTO insertCourseDTO){
       /*{"course_id":"1201",
       "course_name":"�ߵ���ѧ",
       "course_teacher":"����",
       "course_type":"1",
       "course_time":"��������6-7��",
       "course_place":"401",
       "course_capacity":"90",
       "subject_id":"",
       "course_grade":"1",
       "subject_name":"ͨ�Ź���"}
        */

        Integer result = managerService.insertCourse(insertCourseDTO);

        return Result.success();
    }

    @DeleteMapping("/remove_chosen_course")
    @Operation(description = "删除特定选课记录")
    public Result remove_chosen_course_action(CourseSelected courseSelected){
        Integer result =  managerService.removeSelectedCourse(
                courseSelected.getStudent_id(),
                courseSelected.getCourse_id());
        return Result.success();
    }


    @DeleteMapping("/remove_course")
    @Operation(description = "删除课程")
    public Result remove_course_action(Integer course_id){
        Integer result =  managerService.removeCourse(course_id);
        return Result.success();
    }

}
