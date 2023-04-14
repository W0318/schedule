package com.schedule.service;

import com.schedule.entity.Preference;
import com.schedule.mapper.PreferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceService {
    @Autowired
    PreferenceMapper preferenceMapper;

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public void deletePreferenceByEmployeeId(String employeeId) {
        preferenceMapper.deletePreferenceByEmployeeId(employeeId);
    }
    public void insertPreferenceHaveOne(String employeeId, String preferValue) {
        preferenceMapper.insertPreferenceHaveOne(employeeId, preferValue);
    }
    public void insertPreferenceHaveTwo(String employeeId, String preferValue) {
        preferenceMapper.insertPreferenceHaveTwo(employeeId, preferValue);
    }
    public void insertPreferenceHaveThree(String employeeId, String preferValue) {
        preferenceMapper.insertPreferenceHaveThree(employeeId, preferValue);
    }
    public List<Preference> getPreferenceHaveOne(String employeeId) {
        return preferenceMapper.getPreferenceHaveOne(employeeId);
    }
    public List<Preference> getPreferenceHaveTwo(String employeeId) {
        return preferenceMapper.getPreferenceHaveTwo(employeeId);
    }
    public List<Preference> getPreferenceHaveThree(String employeeId) {
        return preferenceMapper.getPreferenceHaveThree(employeeId);
    }
    public void updatePreferenceHaveOne(String employeeId, String preferValue) {
        preferenceMapper.updatePreferenceHaveOne(employeeId, preferValue);
    }
    public void updatePreferenceHaveTwo(String employeeId, String preferValue) {
        preferenceMapper.updatePreferenceHaveTwo(employeeId, preferValue);
    }
    public void updatePreferenceHaveThree(String employeeId, String preferValue) {
        preferenceMapper.updatePreferenceHaveThree(employeeId, preferValue);
    }
    public void deletePreferenceHaveOne(String employeeId) {
        preferenceMapper.deletePreferenceHaveOne(employeeId);
    }
    public void deletePreferenceHaveTwo(String employeeId) {
        preferenceMapper.deletePreferenceHaveTwo(employeeId);
    }
    public void deletePreferenceHaveThree(String employeeId) {
        preferenceMapper.deletePreferenceHaveThree(employeeId);
    public List<Preference> getEmployeePrefer(String employeeId) {
        return preferenceMapper.getEmployeePrefer(employeeId);
    }
}
