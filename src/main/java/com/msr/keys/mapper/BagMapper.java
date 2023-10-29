package com.msr.keys.mapper;

import com.msr.keys.pojo.Bag;
import com.msr.keys.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BagMapper {

    List<Bag> queryBag(String username);

    Bag queryOrderBag(String username, String iname);

    int addBag(String username,String iname,int num,int price);

    int editBag(String username,String iname,int num);

    int deleteBag(String username,String iname);




}