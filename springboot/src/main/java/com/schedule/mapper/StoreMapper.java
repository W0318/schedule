package com.schedule.mapper;

import com.schedule.entity.Employee;
import com.schedule.beans.Storebean;
import com.schedule.entity.Store;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

    List<Store> queryStoreList();
    Store queryStoreById(int id);

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT storeName label FROM store")
    List<Store> getStoreName();

    @Select("select * \n" +
            "FROM store \n" +
            "WHERE storeName like concat('%', #{storeName},'%')")
    List<Store> getStoreByStoreName(String storeName);

    @Select("select * \n" +
            "FROM store \n" +
            "WHERE storeType like concat('%', #{storeType},'%')")
    List<Store> getStoreByStoreType(String storeType);

    @Select("select * \n" +
            "FROM store \n" +
            "WHERE storeName like concat('%', #{storeName},'%')\n" +
            "and storeType like concat('%', #{storeType},'%')")
    List<Store> getStore(String storeName, String storeType);

    //根据员工所在门店Id查找该门店名
    @Select("SELECT storeId FROM employee WHERE employeeId = #{employeeId}")
    Store getEmployeeStoreName(String employeeId);

    //寻找是否已有该门店id
    @Select("SELECT storeId FROM store WHERE storeId = #{storeId}")
    List<Store> getFindIfExistStore(String storeId);

    @Select("UPDATE store SET storeName = #{storeName}, storeType = #{storeType}, size = #{size}, manger = #{manger}, address = #{address}, workers = #{workers} WHERE storeId = #{storeId}")
    void updateStore(String storeId, String storeName, String storeType, String address, Float size, String manger, Integer workers);

    @Select("UPDATE store SET state = 1 WHERE storeId = #{storeId} ")
    void updateStoreStateZero(String storeId);

    @Select("UPDATE store SET state = 0 WHERE storeId = #{storeId} ")
    void updateStoreStateOne(String storeId);

    @Delete("DELETE FROM store WHERE storeId = #{storeId}")
    void deleteStore(String storeId);

    @Insert("INSERT INTO store VALUES (#{storeId}, #{storeName}, #{storeType}, #{address}, #{size}, #{manger}, #{workers}, 1)")
    void insertStore(String storeId, String storeName, String storeType, String address, Float size, String manger, Integer workers);
    @Select("SELECT * FROM store WHERE storeId = #{storeId}")
    Store getStoreById(String storeId);
    @Select("select count(*) from employee WHERE storeId = #{storeId}")
    int getStorePersons(String storeid);
    @Select("SELECT * FROM store")
    List<Storebean> getStoresMessage();
}