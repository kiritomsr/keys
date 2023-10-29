package com.msr.keys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer idOrder;
    private String username;
    private String iname;
    private Integer idExpress;
    private Integer num;
    private String state;
    private Integer price;

//    final String notPaid = "notPaid";
//    final String sending = "sending";
//    final String delivered = "delivered";
//    final String canceled = "canceled";
//    final String received = "received";

}