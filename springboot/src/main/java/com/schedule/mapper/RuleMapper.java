package com.schedule.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleMapper {
    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Delete("DELETE FROM rule WHERE storeId = #{storeId}")
    void deleteRuleByStoreId(String storeId);
}