package com.zht.newclassmanager.contoller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zht.newclassmanager.pojo.*;
import com.zht.newclassmanager.pojo.DTO.InsertCourseDTO;
import com.zht.newclassmanager.pojo.DTO.UpdateCourseDTO;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.ManagerService;
import com.zht.utils.toInteger;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/info")
    public String manager_info_action(HttpSession session,
                                      HttpServletResponse response,
                                      @CookieValue(name="account",required = false) String account){
        User user = null;
        if(account!=null){
            user = new User(Integer.parseInt(account),null);
        }else{
            user = (User) session.getAttribute("MANAGER_SESSION");
        }
        Manager manager = managerService.showManagerInfo(user.getAccount());
        Cookie cookie = new Cookie("manager_name",manager.getManager_name());
        //默认跨域的cookie不能相互访问
        cookie.setPath("/");
        response.addCookie(cookie);
        return JSON.toJSONString(manager);
    }

    @GetMapping("/course_selected")
    public String search_course_selected_info_action(@RequestBody String responseBody){

        JSONObject responseJson = JSON.parseObject(responseBody, JSONObject.class);
        Integer student_id = null;
        Integer course_id = null;
        String student_id_string =  responseJson.getString("student_id_for_course_search");
        String course_id_string = responseJson.getString("course_id_for_course_search");

        if(!(student_id_string.equals("")||student_id_string.equals("0"))){
            student_id = Integer.parseInt(responseJson.getString("student_id_for_course_search"));
        }
        if(!(course_id_string.equals("")||course_id_string.equals("0"))){
            course_id = Integer.parseInt(responseJson.getString("course_id_for_course_search"));
        }
        //TODO: 返回数据格式修改
        List<CourseSelected> list= managerService.showCourseSelected(student_id,course_id);

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (CourseSelected cs: list) {
            //{"course_id":1006,"course_name":"���˼����ԭ��","course_teacher":"�����"
            String course = JSON.toJSONString(cs.getCourse()).replace("}","");
            //,"student_id":2019000000,"student_name":"����"}
            String student = JSON.toJSONString(cs.getStudent()).replace("{",",");
            sb.append(course);
            sb.append(student);
            sb.append(",");
        }
        if(sb.length()>1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }


    @GetMapping("/load_subject")
    public String load_subject_action(){
        List<Subject> subjects = managerService.selectAllSubjects();
        return JSON.toJSONString(subjects);
    }


    @GetMapping("/search_course")
    public String search_course_action(@RequestBody String requestBody){
        JSONObject responseJson = JSON.parseObject(requestBody, JSONObject.class);
        String course_id_string = responseJson.getString("course_id");
        String course_name = responseJson.getString("course_name");
        String course_teacher = responseJson.getString("course_teacher");
        Integer course_id = null;
        if(!(course_id_string.equals("")||course_id_string.equals("0"))){
            course_id = Integer.parseInt(course_id_string);
        }
        if(course_name.equals("")){
            course_name = null;
        }
        if(course_teacher.equals("")){
            course_teacher = null;
        }


        List<Course> courses =  managerService.searchCourse(course_id,course_name,course_teacher);

        return JSON.toJSONString(courses);
    }

    @PostMapping("/select_course")
    public String select_course_action(CourseSelected courseSelected){
        Integer result = managerService.chooseCourseForStudent(
                courseSelected.getStudent_id(),
                courseSelected.getCourse_id());

        return JSON.toJSONString(result);
    }

    @PutMapping("/update_course")

    public Result update_course_action(UpdateCourseDTO updateCourseDTO){

        Integer result = managerService.updateCourse(updateCourseDTO);

        return Result.success();
    }

    @PutMapping("/insert_course")
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
    public Result remove_chosen_course_action(CourseSelected courseSelected){
        Integer result =  managerService.removeSelectedCourse(
                courseSelected.getStudent_id(),
                courseSelected.getCourse_id());
        return Result.success();
    }


    @DeleteMapping("/remove_course")
    public Result remove_course_action(Integer course_id){
        Integer result =  managerService.removeCourse(course_id);
        return Result.success();
    }

}
