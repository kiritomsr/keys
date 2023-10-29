package com.msr.keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class KeysApplication {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        SpringApplication.run(KeysApplication.class, args);


    }

}
