package com.schedule.mapper;

import org.apache.ibatis.annotations.Delete;
import com.schedule.entity.Rule;
import org.apache.ibatis.annotations.Insert;
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

    /**
     * @author 尹日尧
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Select("SELECT ruleId,ruleValue from rule WHERE storeId is NULL")
    List<Rule> getRule();

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId IS NULL AND ruleType = '开店规则'")
    List<Rule> getOpenshopRule();

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId IS NULL AND ruleType = '关店规则'")
    List<Rule> getCloseshopRule();

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId IS NULL AND ruleType = '客流规则'")
    List<Rule> getCustomerRule();

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId IS NULL AND ruleType = '班次规则'")
    List<Rule> getClassesRule();

    @Select("UPDATE rule \n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE storeId IS NULL AND ruleType = '开店规则'")
    void updateOpenshop(String ruleValue);

    @Select("UPDATE rule \n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE storeId IS NULL AND ruleType = '关店规则'")
    void updateCloseshop(String ruleValue);

    @Select("UPDATE rule \n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE storeId IS NULL AND ruleType = '客流规则'")
    void updateCustomer(String ruleValue);

    @Select("UPDATE rule \n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE storeId IS NULL AND ruleType = '班次规则'")
    void updateClasses(String ruleValue);

    @Select("UPDATE rule\n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE (storeId = #{storeId} AND ruleType =\"开店规则\")")
    void updateOpenByStoreId(String storeId, String ruleValue);

    @Select("UPDATE rule\n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE (storeId = #{storeId} AND ruleType =\"关店规则\")")
    void updateCloseByStoreId(String storeId, String ruleValue);

    @Select("UPDATE rule\n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE (storeId = #{storeId} AND ruleType =\"客流规则\")")
    void updateCustomerByStoreId(String storeId, String ruleValue);

    @Select("UPDATE rule\n" +
            "SET ruleValue =  #{ruleValue} \n" +
            "WHERE (storeId = #{storeId} AND ruleType =\"班次规则\")")
    void updateClassesByStoreId(String storeId, String ruleValue);

    @Insert("INSERT INTO rule(storeId, ruleType, ruleValue)\n" +
            "VALUES(#{storeId}, '开店规则', #{ruleValue})")
    void insertOpen(String storeId, String ruleValue);

    @Insert("INSERT INTO rule(storeId, ruleType, ruleValue)\n" +
            "VALUES(#{storeId}, '关店规则', #{ruleValue})")
    void insertClose(String storeId, String ruleValue);

    @Insert("INSERT INTO rule(storeId, ruleType, ruleValue)\n" +
            "VALUES(#{storeId}, '客流规则', #{ruleValue})")
    void insertCustomer(String storeId, String ruleValue);

    @Insert("INSERT INTO rule(storeId, ruleType, ruleValue)\n" +
            "VALUES(#{storeId}, '班次规则', #{ruleValue})")
    void insertClasses(String storeId, String ruleValue);

    //门店是否有规则
    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId = #{storeId} AND ruleType = \"开店规则\"")
    List<Rule> getOpenByStoreId(String storeId);

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId = #{storeId} AND ruleType = \"关店规则\"")
    List<Rule> getCloseByStoreId(String storeId);

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId = #{storeId} AND ruleType = \"客流规则\"")
    List<Rule> getCustomerByStoreId(String storeId);

    @Select("SELECT ruleId,ruleValue FROM rule\n" +
            "WHERE storeId = #{storeId} AND ruleType = \"班次规则\"")
    List<Rule> getClassesByStoreId(String storeId);

    @Select("SELECT * from rule")
    List<Rule> getAllRule();


}