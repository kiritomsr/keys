package com.msr.keys.mapper;

import com.msr.keys.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryByUsername(String username);

    int addUser(User user);

    int updateUser(User user);


}
