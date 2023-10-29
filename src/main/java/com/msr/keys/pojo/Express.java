package com.msr.keys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Express {

    private Integer idExpress;
    private String username;
    private String country;
    private String city;
    private String address;
    private String firstname;
    private String lastname;
    private Integer phone;

}