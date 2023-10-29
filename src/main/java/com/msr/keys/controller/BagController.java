package com.msr.keys.controller;


import com.alibaba.fastjson.JSON;
import com.msr.keys.mapper.BagMapper;
import com.msr.keys.mapper.ItemMapper;
import com.msr.keys.pojo.Bag;
import com.msr.keys.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BagController {

    @Autowired
    private BagMapper bagMapper;

    @Autowired
    private ItemMapper itemMapper;


    @RequestMapping("/bagshow")
    public String showBag(Model model, HttpSession session){

        String loginUser = (String) session.getAttribute("loginUser");
        if(loginUser==null){
            return "norda/login-register";
        }

        List<Bag> bagList = bagMapper.queryBag(loginUser);
        model.addAttribute("bagList",bagList);
        model.addAttribute("JSONbagList", JSON.toJSONString(bagList));

        List<Item> itemList = itemMapper.queryItemList();
        model.addAttribute("JSONitemList",JSON.toJSONString(itemList));

        return "norda/cart";
    }

    @RequestMapping("/bagadd")
    public String itemBag(Model model, HttpSession session, HttpServletRequest request){
        String iname= request.getParameter("iname");

        String loginUser = (String) session.getAttribute("loginUser");
        if(loginUser==null){
            return "norda/login-register";
        }

        int flag = 0;
        int nowNum = 0;
        List<Bag> bagList = bagMapper.queryBag(loginUser);
        Item item = itemMapper.queryItemByIname(iname);
        for(Bag bag:bagList){
            if(iname.equals(bag.getIname())){
                nowNum = bag.getNum();
                flag=1;
            }
        }

        if(flag==0){
            bagMapper.addBag(loginUser,iname,1,item.getPrice());
        }else{
            bagMapper.editBag(loginUser,iname,nowNum+1);
        }

        return showBag(model,session);
    }

    @RequestMapping("/bagreduce")
    public String editBag(Model model, HttpSession session, HttpServletRequest request){
        String iname= request.getParameter("iname");
        String loginUser = (String) session.getAttribute("loginUser");
        int flag = 0;
        int nowNum = 0;
        List<Bag> bagList = bagMapper.queryBag(loginUser);


        for(Bag bag:bagList){
            if(iname.equals(bag.getIname())){
                nowNum = bag.getNum();
            }
        }
        if(nowNum>1){
            bagMapper.editBag(loginUser,iname,nowNum-1);
        }else if (nowNum==1){
            bagMapper.deleteBag(loginUser,iname);
        }

        return showBag(model,session);
    }

    @RequestMapping("/bagdelete")
    public String deleteBag(Model model, HttpSession session, HttpServletRequest request){
        String iname= request.getParameter("iname");
        String loginUser = (String) session.getAttribute("loginUser");

        bagMapper.deleteBag(loginUser,iname);

        return showBag(model,session);
    }

    @RequestMapping("/bagcheckOut")
    public String checkOut(String sendList, Model model, HttpSession session){

        System.out.println(sendList);

        return showBag(model,session);
    }


}
