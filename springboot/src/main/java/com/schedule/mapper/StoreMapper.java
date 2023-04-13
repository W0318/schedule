package com.schedule.mapper;

import com.schedule.beans.Storebean;
import com.schedule.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT * FROM store")
    List<Store> getAllStore();

    @Select("SELECT * FROM store WHERE storeId = #{storeId}")
    Store getStoreById(String storeId);
    @Select("select count(*) from employee WHERE storeId = #{storeId}")
    int getStorePersons(String storeid);
    @Select("SELECT * FROM store")
    List<Storebean> getStoresMessage();
}