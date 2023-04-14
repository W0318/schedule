package com.schedule.mapper;

import com.schedule.entity.Preference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PreferenceMapper {
    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("DELETE FROM preference WHERE employeeId = #{employeeId}")
    void deletePreferenceByEmployeeId(String employeeId);

    @Insert("INSERT INTO preference(employeeId, preferType, preferValue) VALUES(#{employeeId},\"工作日偏好\", #{preferValue})")
    void insertPreferenceHaveOne(String employeeId, String preferValue);

    @Insert("INSERT INTO preference(employeeId, preferType, preferValue) VALUES(#{employeeId},\"工作时间偏好\", #{preferValue})")
    void insertPreferenceHaveTwo(String employeeId, String preferValue);

    @Insert("INSERT INTO preference(employeeId, preferType, preferValue) VALUES(#{employeeId},\"班次时长偏好\", #{preferValue})")
    void insertPreferenceHaveThree(String employeeId, String preferValue);

    @Select("SELECT preferValue FROM preference WHERE employeeId = #{employeeId} AND preferType = \"工作日偏好\"")
    List<Preference> getPreferenceHaveOne(String employeeId);

    @Select("SELECT preferValue FROM preference WHERE employeeId = #{employeeId} AND preferType = \"工作时间偏好\"")
    List<Preference> getPreferenceHaveTwo(String employeeId);

    @Select("SELECT preferValue FROM preference WHERE employeeId = #{employeeId} AND preferType = \"班次时长偏好\"")
    List<Preference> getPreferenceHaveThree(String employeeId);

    @Select("UPDATE preference SET preferValue = #{preferValue} WHERE employeeId = #{employeeId} AND preferType = \"工作日偏好\"")
    void updatePreferenceHaveOne(String employeeId, String preferValue);

    @Select("UPDATE preference SET preferValue = #{preferValue} WHERE employeeId = #{employeeId} AND preferType = \"工作时间偏好\"")
    void updatePreferenceHaveTwo(String employeeId, String preferValue);

    @Select("UPDATE preference SET preferValue = #{preferValue} WHERE employeeId = #{employeeId} AND preferType = \"班次时长偏好\"")
    void updatePreferenceHaveThree(String employeeId, String preferValue);

    @Delete("DELETE FROM preference WHERE employeeId = #{employeeId} AND preferType = \"工作日偏好\"")
    void deletePreferenceHaveOne(String employeeId);

    @Delete("DELETE FROM preference WHERE employeeId = #{employeeId} AND preferType = \"工作时间偏好\"")
    void deletePreferenceHaveTwo(String employeeId);

    @Delete("DELETE FROM preference WHERE employeeId = #{employeeId} AND preferType = \"班次时长偏好\"")
    void deletePreferenceHaveThree(String employeeId);

}