package com.msr.keys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bag {

    private String iname;
    private String username;
    private Integer num;
    private Integer price;

}