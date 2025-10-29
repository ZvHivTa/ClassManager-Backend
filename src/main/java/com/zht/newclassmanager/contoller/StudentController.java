package com.zht.newclassmanager.contoller;

import com.alibaba.fastjson.JSON;
import com.zht.newclassmanager.pojo.Course;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @RequestMapping("/personalinfo")
    @ResponseBody
    public String personal_info_action(HttpSession session,
                                       HttpServletResponse response,
                                       @CookieValue(name="account",required = false) String account) throws IOException {
        User user = null;
        if(account!=null){
            user = new User(Integer.parseInt(account),null);
        }else{
            user = (User) session.getAttribute("USER_SESSION");
        }

        Student student = this.studentService.showStudentInfo(user.getAccount());
        Cookie cookie = new Cookie("student_name", student.getStudent_name());
        cookie.setPath("/");
        response.addCookie(cookie);
        return JSON.toJSONString(student);
    }
    @RequestMapping("/suggestion")
    @ResponseBody
    public String show_suggestion_course_action(HttpSession session){
        User user = (User) session.getAttribute("USER_SESSION");
        List<Course> Courses = this.studentService.showSuggestedCourses(user.getAccount());
        return JSON.toJSONString(Courses);
    }

    @RequestMapping("/choose_course")
    @ResponseBody
    public String choose_course_action(HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("USER_SESSION");
        Integer course_id= Integer.parseInt(request.getParameter("course_id"));
        Integer result = this.studentService.chooseCourse(user.getAccount(),course_id);
        return JSON.toJSONString(result);
    }

    @RequestMapping("/remove_chosen_course")
    @ResponseBody
    public String remove_chosen_course_action(HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("USER_SESSION");
        Integer course_id= Integer.parseInt(request.getParameter("course_id"));
        Integer result = this.studentService.removeChosenCourse(user.getAccount(),course_id);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/optional")
    @ResponseBody
    public String show_optional_course_action(){
        List<Course> courses = this.studentService.showOptionalCourses();
        return JSON.toJSONString(courses);
    }

    @RequestMapping("/chosen")
    @ResponseBody
    public String show_chosen_course_action(HttpSession session){
        User user = (User) session.getAttribute("USER_SESSION");
        List<Course> courses = this.studentService.showChosenCourses(user.getAccount());
        return JSON.toJSONString(courses);
    }


}
