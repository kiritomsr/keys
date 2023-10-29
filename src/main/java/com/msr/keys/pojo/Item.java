package com.msr.keys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String iname;
    private String type;
    private Integer price;
    private Integer storage;
    private String ipic;

}
