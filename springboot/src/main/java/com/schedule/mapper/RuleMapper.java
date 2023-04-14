package com.schedule.mapper;

import org.apache.ibatis.annotations.Delete;
import com.schedule.entity.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleMapper {
    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Delete("DELETE FROM rule WHERE storeId = #{storeId}")
    void deleteRuleByStoreId(String storeId);
    @Select("SELECT * FROM rule WHERE storeId IS NULL")
    List<Rule> getGeneralRule();

    @Select("SELECT * FROM rule WHERE storeId = #{storeId}")
    List<Rule> getStoreRule(String storeId);
}