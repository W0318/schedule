package com.schedule.mapper;

import com.schedule.entity.FormDatePeriod;
import com.schedule.entity.Scheduling;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Mapper
public interface SchedulingMapper {
    @Select("SELECT DISTINCT day, employeeIds FROM scheduling WHERE employeeIds LIKE concat('%', #{employeeId}, ',%') OR employeeIds LIKE concat('%,', #{employeeId}, '%') OR employeeIds = #{employeeId} ORDER BY day")
    List<FormDatePeriod> getEmployeeWorkday(String employeeId);

    @Select("SELECT CONCAT(startTime, '-', endTime) period, employeeIds FROM scheduling WHERE (employeeIds LIKE concat('%', #{employeeId}, ',%') OR employeeIds LIKE concat('%,', #{employeeId}, '%') OR employeeIds = #{employeeId}) AND day = #{day} ORDER BY startTime")
    List<FormDatePeriod> getEmployeeDayWork(String employeeId, Date day);

    @Select("SELECT * FROM scheduling WHERE day >= #{Monday} AND day <= #{Sunday} AND storeId = #{storeId} ORDER BY day, startTime")
    List<Scheduling> getAWeekWork(Date Monday, Date Sunday, String storeId);

    @Select("SELECT * FROM scheduling\n" +
            "WHERE day >= #{Monday} AND day <= #{Sunday} AND storeId = #{storeId}\n" +
            "AND (employeeIds LIKE concat('%', #{employeeId}, ',%') OR employeeIds LIKE concat('%,', #{employeeId}, '%') OR employeeIds = #{employeeId})\n"
            +
            "ORDER BY day, startTime")
    List<Scheduling> getStuffWeekWork(Date Monday, Date Sunday, String storeId, String employeeId);

    @Select("SELECT * FROM scheduling WHERE day = #{day} AND storeId = #{storeId} ORDER BY startTime")
    List<Scheduling> getADayWork(Date day, String storeId);

    @Select("SELECT * FROM scheduling\n" +
            "WHERE day = #{day} AND storeId = #{storeId}\n" +
            "AND (employeeIds LIKE concat('%', #{employeeId}, ',%') OR employeeIds LIKE concat('%,', #{employeeId}, '%') OR employeeIds = #{employeeId})\n"
            +
            "ORDER BY startTime")
    List<Scheduling> getStuffDayWork(Date day, String storeId, String employeeId);

    @Select("SELECT * FROM scheduling WHERE id = #{id}")
    Scheduling getById(int id);

    @Select("SELECT * FROM scheduling WHERE day = #{day} AND startTime = #{startTime} AND storeId = #{storeId}")
    Scheduling getByTime(Date day, Time startTime, String storeId);

    @Delete("DELETE FROM scheduling WHERE id in (${ids})")
    int deleteScheduling(String ids);

    @Insert("INSERT INTO scheduling(storeId, employeeIds, day, startTime, endTime, periodName) VALUES (#{storeId}, #{employeeIds}, #{day}, #{startTime}, #{endTime}, #{periodName})")
    void insertScheduling(Scheduling scheduling);

    @Update("UPDATE scheduling SET employeeIds = #{employeeIds} WHERE id = #{id}")
    void updateScheduling(String employeeIds, int id);

    @Update("UPDATE scheduling SET employeeIds = #{employeeIds} WHERE day = #{day} AND startTime = #{startTime} AND storeId = #{storeId}")
    void updateEmployeeIds(String employeeIds, Date day, Time startTime, String storeId);

    @Delete("DELETE FROM scheduling WHERE day = #{day} AND startTime = #{startTime} AND storeId = #{storeId}")
    void deleteEmployeeIds(Date day, Time startTime, String storeId);

    @Delete("DELETE FROM scheduling WHERE storeId = #{storeId}")
    void deleteSchedulingByStoreId(String storeId);
}