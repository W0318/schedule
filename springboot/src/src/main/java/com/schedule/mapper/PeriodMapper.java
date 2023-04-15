package com.schedule.mapper;

import com.schedule.entity.Period;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PeriodMapper {
    @Select("SELECT * FROM period WHERE storeId IS NULL AND type = 'all' ORDER BY periodName")
    List<Period> getGeneralPeriodAll();

    @Select("SELECT * FROM period WHERE storeId = #{storeId} AND type = 'all' ORDER BY periodName")
    List<Period> getStorePeriodAll(String storeId);

    @Select("SELECT * FROM period WHERE storeId IS NULL AND type = 'weekday' ORDER BY periodName")
    List<Period> getGeneralPeriodWeekday();

    @Select("SELECT * FROM period WHERE storeId = #{storeId} AND type = 'weekday' ORDER BY periodName")
    List<Period> getStorePeriodWeekday(String storeId);

    @Select("SELECT * FROM period WHERE storeId IS NULL AND type = 'weekend' ORDER BY periodName")
    List<Period> getGeneralPeriodWeekend();

    @Select("SELECT * FROM period WHERE storeId = #{storeId} AND type = 'weekend' ORDER BY periodName")
    List<Period> getStorePeriodWeekend(String storeId);

    @Insert("INSERT INTO period(storeId, periodName, type) VALUES(#{storeId}, #{periodName}, #{type})")
    void insertPeriod(String storeId, String periodName, String type);

    @Update("UPDATE period SET periodName = #{periodName} WHERE periodId = #{periodId}")
    void updatePeriod(int periodId, String periodName);

    @Delete("DELETE FROM period WHERE periodId = #{periodId}")
    void deletePeriod(int periodId);
}