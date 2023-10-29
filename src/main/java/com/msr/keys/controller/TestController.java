package com.msr.keys.controller;


import com.msr.keys.mapper.UserMapper;
import com.msr.keys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @RequestMapping("/test/01")
    public String test01(boolean[] cbif, Model model) throws IOException {
//        String data = "";
//        for(int i=0;i<=cbif.length;i++){
//            data = data + i;
//        }
//
//        System.out.println(data);
//        //model.addAttribute("data",data);

        return "test";
    }

    @RequestMapping("/test/req/02")
    public String testEnter(Model model){
        List<String> bagList = new ArrayList<>();
        bagList.add("1");
        bagList.add("2");
        bagList.add("3");
        bagList.add("4");

        model.addAttribute("cbList",bagList);
        return "test02";
    }

    @RequestMapping("/test/res/02")
    public String testFeedback(String sendList, HttpServletRequest request){

        System.out.println("to res.");
        //System.out.println(sendList);
        return "test02";
    }

    @RequestMapping("/test/req/03")
    public String testEnter03(Model model){

        List<String> bagList = new ArrayList<>();
        bagList.add("111");
        bagList.add("112");
        bagList.add("113");
        bagList.add("114");

        model.addAttribute("cbList",bagList);

        return "test03";
    }




}
