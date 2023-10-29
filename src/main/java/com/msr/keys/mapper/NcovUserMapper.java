package com.msr.keys.mapper;

import com.msr.keys.pojo.NcovUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NcovUserMapper {

    List<NcovUser> queryNcovUserList();

    NcovUser queryByNcovUsername(int id);

    int addNcovUser(NcovUser user);

    int updateNcovUser(NcovUser user);


}
