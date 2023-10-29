package com.msr.keys.controller;


import com.msr.keys.mapper.NcovUserMapper;
import com.msr.keys.mapper.UserMapper;
import com.msr.keys.pojo.NcovUser;
import com.msr.keys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class NcovController {

    @Autowired
    private NcovUserMapper userMapper;

    @RequestMapping("/ncov")
    public String toMain(Model model,HttpSession session){
        return "norda/login-ncov";
    }

    @RequestMapping("/ncovlogin")
    public String ncovLogin(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){

        List<NcovUser> userList = userMapper.queryNcovUserList();
        for(NcovUser user:userList){
            if(!StringUtils.isEmpty(username) && user.getId().equals(username) ){
                if(!StringUtils.isEmpty(password) && user.getPwd().equals(password) ){
                    session.setAttribute("loginUser",username);
                    return toMain(model, session);
                }
            }
        }
        model.addAttribute("msg","Wrong username or password");
        return "norda/login-register";
    }



}
