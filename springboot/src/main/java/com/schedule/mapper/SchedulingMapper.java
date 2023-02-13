package com.schedule.mapper;

import com.schedule.entity.Scheduling;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Mapper
public interface SchedulingMapper {
    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT DISTINCT day FROM scheduling WHERE employeeIds LIKE concat(#{employeeId},'%') OR employeeIds LIKE concat('%',#{employeeId}) OR employeeIds = #{employeeId} ORDER BY day")
    List<Date> getEmployeeWorkday(String employeeId);

    @Select("SELECT CONCAT(startTime, '-', endTime) period FROM scheduling WHERE (employeeIds LIKE concat(#{employeeId},'%') OR employeeIds LIKE concat('%',#{employeeId}) OR employeeIds = '1') AND day = #{day} ORDER BY startTime")
    List<String> getEmployeeDaywork(String employeeId, Date day);

    @Select("SELECT * FROM scheduling WHERE day >= #{Monday} AND day <= #{Sunday} AND storeId = #{storeId} ORDER BY day, startTime")
    List<Scheduling> getAWeekwork(Date Monday, Date Sunday, String storeId);

    @Select("SELECT * FROM scheduling WHERE day = #{day} AND storeId = #{storeId} ORDER BY startTime")
    List<Scheduling> getADaywork(Date day, String storeId);

    @Select("SELECT * FROM scheduling WHERE id = #{id}")
    Scheduling getById(int id);

    @Delete("DELETE FROM scheduling WHERE id in (${ids})")
    int deleteScheduling(String ids);

    @Insert("INSERT INTO scheduling(storeId, employeeIds, day, startTime, endTime, periodName) VALUES (#{storeId}, #{employeeIds}, #{day}, #{startTime}, #{endTime}, #{periodName})")
    int insertScheduling(String storeId, String employeeIds, Date day, Time startTime, Time endTime, String periodName);

    @Update("UPDATE scheduling SET employeeIds = #{employeeIds} WHERE id = #{id}")
    int updateScheduling(String employeeIds, int id);
}