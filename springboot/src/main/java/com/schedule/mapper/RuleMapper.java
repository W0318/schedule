package com.schedule.mapper;

import com.schedule.entity.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleMapper {
    @Select("SELECT * FROM rule WHERE storeId IS NULL")
    List<Rule> getGeneralRule();

    @Select("SELECT * FROM rule WHERE storeId = #{storeId}")
    List<Rule> getStoreRule(String storeId);
}