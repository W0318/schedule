package com.schedule.mapper;

import com.schedule.entity.Preference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PreferenceMapper {
    @Select("SELECT * FROM preference WHERE employeeId = #{employeeId}")
    List<Preference> getEmployeePrefer(String employeeId);
}