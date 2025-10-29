package com.zht.newclassmanager.contoller;

import com.zht.newclassmanager.pojo.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//控制侧边导航栏跳转的页面控制器
@RestController
public class PageController {
    @RequestMapping("/")
    public String jump_to_login_action(){
        return "/login.html";
    }
    //学生界面跳转
    @RequestMapping("/jumpToPersonalInfo")
    public String jump_to_personalinfo_action(){
        return "personalinfo";
    }
    @RequestMapping("/jumpToSuggestion")
    public String jump_to_suggestion_action(){
        return "suggestion";
    }
    @RequestMapping("/jumpToOptional")
    public String jump_to_optional_action(){
        return "optional";
    }
    @RequestMapping("/jumpToChosen")
    public String jump_to_chosen_action(){
        return "chosen";
    }

    //跳转登录界面
    @RequestMapping("/jumpTologin")
    public String jump_to_login_action(HttpServletRequest request, HttpSession session){
        //有无cookie存储
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("type")) {
                    if (cookie.getValue().equals("1")) {
                        return "personalinfo";
                    } else if(cookie.getValue().equals("0")){
                        return "managerinfo";
                    }
                }
            }
        }

        //session，是否登陆过
        User user1 = (User) session.getAttribute("USER_SESSION");
        User user2 = (User) session.getAttribute("MANAGER_SESSION");
        if((user1!=null)&&(user2==null)){
            return "personalinfo";
        }else if((user1==null)&&(user2!=null)){
            return "managerinfo";
        }
        return "login";
    }

    //管理员界面跳转
    @RequestMapping("/jumpToManagerInfo")
    public String jump_to_managerinfo_action(){
        return "managerinfo";
    }


    @RequestMapping("/jumpToStudentManagement")
    public String jump_to_studentManagement_action(){
        return "student_management";
    }

    @RequestMapping("/jumpToCourseManagement")
    public String jump_to_courseManagement_action(){
        return "course_management";
    }
    @RequestMapping("/jumpToError")
    public String jump_to_error_action(HttpServletRequest request){
        String errorCode = request.getParameter("errorCode");
        if(errorCode.equals("405")){
            return "405";
        }
        return "404";
    }


    //安全退出
    @RequestMapping("/safely_exit")
    @ResponseBody
    public String safely_exit_action(HttpSession session, HttpServletResponse response){

        session.invalidate();

        Cookie cookie = new Cookie("account",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie cookie2 = new Cookie("type",null);
        cookie2.setMaxAge(0);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        return "";
    }
}
