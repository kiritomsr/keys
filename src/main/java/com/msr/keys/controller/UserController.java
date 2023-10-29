package com.msr.keys.controller;


import com.alibaba.fastjson.JSONObject;
import com.msr.keys.mapper.UserMapper;
import com.msr.keys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping({"/","/mainnull","/main"})
    public String toMain(Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null) {
            model.addAttribute("msg",null);
        }else {
            model.addAttribute("msg","");
        }
        return "norda/index";
    }

    @RequestMapping("/old")
    public String toMainOld(Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null) {
            model.addAttribute("msg",null);
        }else {
            model.addAttribute("msg","");
        }
        return "mainnull";
    }

    @RequestMapping("/sign")
    public String detSign(Model model,HttpSession session){
        if(session.getAttribute("loginUser")==null) {
            return toLogin();
        }else return logout(model, session);
    }

    @RequestMapping("/login")
    public String toLogin(){
        return "norda/login-register";
    }

    @RequestMapping("/logout")
    public String logout(Model model,HttpSession session){
        session.setAttribute("loginUser",null);
        return toMain(model, session);
    }


    @RequestMapping("/userlogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){

        List<User> userList = userMapper.queryUserList();
        for(User user:userList){
            if(!StringUtils.isEmpty(username) && user.getUsername().equals(username) ){
                if(!StringUtils.isEmpty(password) && user.getPassword().equals(password) ){
                    session.setAttribute("loginUser",username);
                    return toMain(model, session);
                }
            }
        }
        model.addAttribute("msg","Wrong username or password");
        return "norda/login-register";
    }

    @RequestMapping("/userreg")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                            @RequestParam("email") String email,
                        Model model){
        int flag = 0;
        List<User> userList = userMapper.queryUserList();
        for(User user:userList){
            if(user.getUsername().equals(username)){
                flag=1;
                model.addAttribute("msg2","invalid username");
                return toLogin();
            }
        }

        for(User user:userList){
            if(user.getEmail().equals(email)){
                flag = 1;
                model.addAttribute("msg2","invalid email");
                return toLogin();
            }
        }
        System.out.println(flag);

        if(flag==0){
            userMapper.addUser(new User(username,password,email));
        }
        return toLogin();
    }

    @RequestMapping("/checkUsername")
    public void checkUsername(@RequestParam("username") String username,
                              HttpServletResponse httpServletResponse) throws IOException {
        List<User> userList = userMapper.queryUserList();
        for(User user:userList){
            if(StringUtils.isEmpty(username) || user.getUsername().equals(username) ){
                httpServletResponse.getWriter().print("no");
                return;
            }
        }
        httpServletResponse.getWriter().print("yes");
    }

    @RequestMapping("/checkEmail")
    public void checkEmail(@RequestParam("email") String email,
                              HttpServletResponse httpServletResponse) throws IOException {
        List<User> userList = userMapper.queryUserList();
        for(User user:userList){
            if(StringUtils.isEmpty(email) || user.getEmail().equals(email) ){
                httpServletResponse.getWriter().print("no");
                return;
            }
        }
        httpServletResponse.getWriter().print("yes");
    }

    @RequestMapping("/testPython")
    public void testPython(HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("get called.");
        httpServletResponse.getWriter().print("showback");
        }

}
