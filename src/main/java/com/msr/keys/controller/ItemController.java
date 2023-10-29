package com.msr.keys.controller;


import com.msr.keys.mapper.ItemMapper;
import com.msr.keys.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemMapper itemMapper;

    @RequestMapping("/itemLoad")
    public String loadItems(Model model){

        List<Item> itemList = itemMapper.queryItemList();

        model.addAttribute("itemList",itemList);

        return "norda/shop";
    }

    @RequestMapping("/itemDetail")
    public String detailItem(Model model, HttpServletRequest request){

        String iname= request.getParameter("iname");
        Item thisitem = itemMapper.queryItemByIname(iname);

        model.addAttribute("thisItem",thisitem);

        return "norda/product-details";
    }

    @RequestMapping("/itemselect")
    public String selectItems(@RequestParam("type") String type,
            Model model){

        List<Item> itemList = itemMapper.queryItemType(type);

        model.addAttribute("itemList",itemList);

        return "norda/shop";
    }

    @RequestMapping("/itemsearch")
    public String searchItems(@RequestParam("search") String search,
                              Model model){
        search = '%' + search + '%';

        List<Item> itemList = itemMapper.queryItemSearch(search);

        model.addAttribute("itemList",itemList);

        return "norda/shop";
    }


}
