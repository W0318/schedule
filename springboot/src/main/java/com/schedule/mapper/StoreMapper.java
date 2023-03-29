package com.schedule.mapper;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
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
    List<Employee> getStoreEmployee(Store store);
}