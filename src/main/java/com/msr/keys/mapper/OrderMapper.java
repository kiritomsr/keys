package com.msr.keys.mapper;

import com.msr.keys.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {


    int queryidOrder();

    List<Order> queryOrder(String username);

    Order queryOrderById(int idOrder);

    int addOrder(int idOrder, String username, String iname,
                   int idExpress, int num, String state, int price);

    int editOrderAddress(int idOrder, int idExpress);

    int editOrderState(int idOrder, String state);
}