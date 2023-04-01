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

    public List<Preference> getEmployeePrefer(String employeeId) {
        return preferenceMapper.getEmployeePrefer(employeeId);
    }
}
