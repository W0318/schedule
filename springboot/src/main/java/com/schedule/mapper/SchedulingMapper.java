package com.schedule.mapper;

import com.schedule.entity.Scheduling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

@Mapper
public interface SchedulingMapper {
    @Select("SELECT DISTINCT day FROM scheduling WHERE employeeIds LIKE concat(#{employeeId},'%') OR employeeIds LIKE concat('%',#{employeeId}) OR employeeIds = #{employeeId} ORDER BY day")
    List<Date> getEmployeeWorkday(String employeeId);

    @Select("SELECT CONCAT(startTime, '-', endTime) period FROM scheduling WHERE (employeeIds LIKE concat(#{employeeId},'%') OR employeeIds LIKE concat('%',#{employeeId}) OR employeeIds = '1') AND day = #{day} ORDER BY startTime")
    List<String> getEmployeeDaywork(String employeeId, Date day);

    @Select("SELECT * FROM scheduling WHERE day >= #{Monday} AND day <= #{Sunday} ORDER BY day, startTime")
    List<Scheduling> getAWeekwork(Date Monday, Date Sunday);
}