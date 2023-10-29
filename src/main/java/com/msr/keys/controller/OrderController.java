package com.msr.keys.controller;


import com.alibaba.fastjson.JSON;
import com.msr.keys.mapper.*;
import com.msr.keys.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private BagMapper bagMapper;

    @Autowired
    private ExpressMapper expressMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/ordersubmit")
    public String submitOrder(
                              Model model, HttpSession session,HttpServletRequest request){
        String sum= request.getParameter("sum");
        //@RequestParam("sendList") String sendList,
        String sendList= request.getParameter("sendList");
        model.addAttribute("sendList",sendList);
        String[] sendArray = sendList.split(",");
        model.addAttribute("sum",sum);

        String loginUser = (String) session.getAttribute("loginUser");
        if(sendArray.length == 0) {
            List<Bag> bagList = bagMapper.queryBag(loginUser);
            model.addAttribute("bagList",bagList);
            return "norda/checkout";
        }

        List<Bag> orderBagList = new ArrayList<>();
        for(String iname : sendArray){ orderBagList.add(bagMapper.queryOrderBag(loginUser,iname)); }
        model.addAttribute("orderBagList",orderBagList);

        List<Express> expressList = expressMapper.queryExpress(loginUser);
        model.addAttribute("expressList",expressList);

        return "norda/checkout";
    }

    @RequestMapping("/orderconfirm")
    public String confirmOrder(
            @RequestParam("sendList") String sendList,
            @RequestParam("idExpress") String idExpress,
            Model model, HttpSession session){

        model.addAttribute("sendList",sendList);
        String[] sendArray = sendList.split(",");
        String loginUser = (String) session.getAttribute("loginUser");
        int expressId = Integer.parseInt(idExpress);

        for(String iname : sendArray) {
            Bag bag = bagMapper.queryOrderBag(loginUser,iname);
            int  nowNum =  orderMapper.queryidOrder() + 1;
            System.out.println(nowNum);
            orderMapper.addOrder(nowNum,loginUser,iname,expressId,bag.getNum(),"notPaid",bag.getPrice());
            bagMapper.deleteBag(loginUser,iname);
        }

        return toCenter(model, session);
    }

    public String toCenter(Model model, HttpSession session){

        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );

        model.addAttribute("currentUsername",currentUser.getUsername());
        model.addAttribute("currentEmail",currentUser.getEmail());
        model.addAttribute("currentPassword",currentUser.getPassword());

        List<Express> expressList = expressMapper.queryExpress(currentUser.getUsername());

        expressList.add(new Express(0,currentUser.getUsername(),"","","","","",0));
        model.addAttribute("expressList",expressList);
        model.addAttribute("JSONexpressList", JSON.toJSONString(expressList));

        //todo 10.7 sql error
        List<Order> orderList = orderMapper.queryOrder(currentUser.getUsername());
        model.addAttribute("orderList",orderList);
        model.addAttribute("JSONorderList",JSON.toJSONString(orderList));

        return "norda/my-account";
    }

}
