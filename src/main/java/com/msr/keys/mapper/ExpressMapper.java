package com.msr.keys.mapper;

import com.msr.keys.pojo.Bag;
import com.msr.keys.pojo.Express;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExpressMapper {

    List<Express> queryExpress(String username);

    int addExpress(int idExpress, String username,
                   String country, String city, String address,
                   String firstname, String lastname, int phone);

    int editExpress(int idExpress, String username,
                    String country, String city, String address,
                    String firstname, String lastname, int phone);

    int deleteExpress(int idExpress, String username);




}