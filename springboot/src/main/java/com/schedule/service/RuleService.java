package com.schedule.service;

import com.schedule.entity.Rule;
import com.schedule.mapper.RuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {
    @Autowired
    RuleMapper ruleMapper;

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public void deleteRuleByStoreId(String storeId) {
        ruleMapper.deleteRuleByStoreId(storeId);
    }
    public List<Rule> getGeneralRule() {
        return ruleMapper.getGeneralRule();
    }

    public List<Rule> getStoreRule(String storeId) {
        return ruleMapper.getStoreRule(storeId);
    }

    /**
     * @author 尹日尧
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Rule> getRule() {
        return ruleMapper.getRule();
    }

    public List<Rule> getOpenshopRule(){
        return ruleMapper.getOpenshopRule();
    }

    public List<Rule> getCustomerRule(){
        return ruleMapper.getCustomerRule();
    }

    public List<Rule> getClassesRule(){
        return ruleMapper.getClassesRule();
    }

    public List<Rule> getCloseshopRule(){
        return ruleMapper.getCloseshopRule();
    }

    public void updateOpenshop(String ruleValue) {
        ruleMapper.updateOpenshop(ruleValue);
    }

    public void updateCloseshop(String ruleValue) {
        ruleMapper.updateCloseshop(ruleValue);
    }

    public void updateCustomer(String ruleValue) {
        ruleMapper.updateCustomer(ruleValue);
    }

    public void updateClasses(String ruleValue) {
        ruleMapper.updateClasses(ruleValue);
    }

    public void updateOpenByStoreId(String storeId, String ruleValue) {
        ruleMapper.updateOpenByStoreId(storeId, ruleValue);
    }

    public void updateCloseByStoreId(String storeId, String ruleValue) {
        ruleMapper.updateCloseByStoreId(storeId, ruleValue);
    }

    public void updateCustomerByStoreId(String storeId, String ruleValue) {
        ruleMapper.updateCustomerByStoreId(storeId, ruleValue);
    }

    public void updateClassesByStoreId(String storeId, String ruleValue) {
        ruleMapper.updateClassesByStoreId(storeId, ruleValue);
    }

    public List<Rule> getOpenByStoreId(String storeId){
        return ruleMapper.getOpenByStoreId(storeId);
    }

    public List<Rule> getCloseByStoreId(String storeId){
        return ruleMapper.getCloseByStoreId(storeId);
    }

    public List<Rule> getCustomerByStoreId(String storeId){
        return ruleMapper.getCustomerByStoreId(storeId);
    }

    public List<Rule> getClassesByStoreId(String storeId){
        return ruleMapper.getClassesByStoreId(storeId);
    }

    public void insertOpen(String ruleId, String storeId, String ruleValue){
        ruleMapper.insertOpen(ruleId, storeId, ruleValue);
    }

    public void insertClose(String ruleId, String storeId, String ruleValue){
        ruleMapper.insertClose(ruleId, storeId, ruleValue);
    }

    public void insertCustomer(String ruleId, String storeId, String ruleValue){
        ruleMapper.insertCustomer(ruleId, storeId, ruleValue);
    }

    public void insertClasses(String ruleId, String storeId, String ruleValue){
        ruleMapper.insertClasses(ruleId, storeId, ruleValue);
    }

    public List<Rule> getAllRule() {
        return ruleMapper.getAllRule();
    }


}
