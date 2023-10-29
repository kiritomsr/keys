package com.msr.keys.mapper;

import com.msr.keys.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ItemMapper {

    List<Item> queryItemList();

    List<Item> queryItemType(String type);

    List<Item> queryItemSearch(String search);

    Item queryItemByIname(String iname);

}
