package com.schedule.mapper;

import com.schedule.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ScheduleMapper {
    List<Employee> queryEmployeeByStoreId(String str);
}
