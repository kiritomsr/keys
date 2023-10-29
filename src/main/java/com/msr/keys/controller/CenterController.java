package com.msr.keys.controller;


import com.alibaba.fastjson.JSON;
import com.msr.keys.mapper.*;
import com.msr.keys.pojo.Bag;
import com.msr.keys.pojo.Express;
import com.msr.keys.pojo.Order;
import com.msr.keys.pojo.User;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class CenterController {

    @Autowired
    private ExpressMapper expressMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;


    @RequestMapping("/centershow")
    public String toCenter(Model model, HttpSession session){

        if((String) session.getAttribute("loginUser")==null){
            return "norda/login-register";
        }

        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );

        model.addAttribute("currentUsername",currentUser.getUsername());
        model.addAttribute("currentEmail",currentUser.getEmail());
        model.addAttribute("currentPassword",currentUser.getPassword());

        List<Express> expressList = expressMapper.queryExpress(currentUser.getUsername());

        expressList.add(new Express(0,currentUser.getUsername(),"","","","","",0));
        model.addAttribute("expressList",expressList);
        model.addAttribute("JSONexpressList",JSON.toJSONString(expressList));

        //todo 10.7 sql error
        List<Order> orderList = orderMapper.queryOrder(currentUser.getUsername());
        model.addAttribute("orderList",orderList);
        model.addAttribute("JSONorderList",JSON.toJSONString(orderList));

        return "norda/my-account";
    }

    @RequestMapping("/userpersonal")
    public String changePerson(
            @RequestParam("password") String inputPassword,
            @RequestParam("email") String inputEmail,
            Model model,HttpSession session){
        int flag = 0;
        List<User> userList = userMapper.queryUserList();
        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );
        String updatePassword = currentUser.getPassword();
        String updateEmail = currentUser.getEmail();

        if(!inputPassword.isEmpty()) {
            updatePassword = inputPassword;
        }

        if(!inputEmail.isEmpty()) {
            updateEmail = inputEmail;
            for(User user:userList){
                if( user.getEmail().equals(updateEmail) ){
                    flag = 2;
                    model.addAttribute("msg3","invalid email");
                    return toCenter(model,session);
                }
            }
        }
        System.out.println(flag);

        if(flag==0){
            userMapper.updateUser(new User(currentUser.getUsername(),updatePassword,updateEmail));
        }

        return toCenter(model,session);
    }

    @RequestMapping("/expressedit")
    public String submitExpress(
            @RequestParam("idExpress") int idExpress,
            @RequestParam("country") String country,
            @RequestParam("firstname") String firstname,
            @RequestParam("city") String city,
            @RequestParam("lastname") String lastname,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            Model model,HttpSession session){
        int parsePhone;
        if(phone == ""){
            parsePhone = 0;
        }else {
            try {
                parsePhone = Integer.parseInt(phone);
            }catch (NumberFormatException e){
                e.printStackTrace();
                return toCenter(model,session);
            }
        }
        //todo: 10.6
        if(idExpress==0) {
            newExpress(idExpress, country, firstname, city, lastname, address, parsePhone, model,session);
        }else {
            editExpress(idExpress, country, firstname, city, lastname, address, parsePhone, model,session);
        }

        return toCenter(model,session);
    }

    public void newExpress( int idExpress, String country, String firstname, String city, String lastname, String address, int phone, Model model, HttpSession session){

        if(country == "") { return; }
        if(firstname == "") {return;}
        if(lastname == "") {return;}
        if(city == "") {return;}
        if(address == "") {return;}
        if(phone == 0) {return;}

        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );
        List<Express> expressList = expressMapper.queryExpress(currentUser.getUsername());
        int newNum;
        if(expressList.size()==0){
            newNum = 1;
        }else {
            newNum= expressList.get(expressList.size()-1).getIdExpress() + 1;
        }
        expressMapper.addExpress(newNum,currentUser.getUsername(),country,city,address,firstname,lastname,phone);

    }

    public void editExpress( int idExpress, String country, String firstname, String city, String lastname, String address, int phone, Model model, HttpSession session){
        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );
        List<Express> expressList = expressMapper.queryExpress(currentUser.getUsername());
        Express currentExpress = null;
        for(Express express:expressList){
            if(idExpress == express.getIdExpress()){
                currentExpress = express;
                break;
            }
        }
        if(currentExpress == null) return;
        if(country == "") { country = currentExpress.getCountry(); }
        if(firstname == "") { firstname = currentExpress.getFirstname();}
        if(lastname == "") {lastname = currentExpress.getLastname();}
        if(city == "") {city = currentExpress.getCity();}
        if(address == "") {address = currentExpress.getAddress();}
        if(phone == 0) {phone = currentExpress.getPhone();}

        expressMapper.editExpress(idExpress,currentUser.getUsername(),country,city,address,firstname,lastname,phone);
    }

    @RequestMapping("/expressdelete")
    public String deleteExpress( @RequestParam("idExpress") int idExpress, Model model,HttpSession session){
        User currentUser = userMapper.queryByUsername( (String) session.getAttribute("loginUser") );
        expressMapper.deleteExpress(idExpress,currentUser.getUsername());
        return toCenter(model,session);
    }

    @RequestMapping("/orderforward")
    public String forwardOrder(  @RequestParam("idOrder") int idOrder, @RequestParam("forward") String forwardType,
            Model model,HttpSession session){

        Order order = orderMapper.queryOrderById(idOrder);

        if(forwardType.equals("canceled")){ orderMapper.editOrderState(idOrder,"canceled"); }
        if(forwardType.equals("forward")){
            if(order.getState().equals("notPaid")) {orderMapper.editOrderState(idOrder,"sending"); }
            if(order.getState().equals("sending")) {orderMapper.editOrderState(idOrder,"delivered"); }
            if(order.getState().equals("delivered")) {orderMapper.editOrderState(idOrder,"received"); }
        }

        return toCenter(model, session);
    }









}
