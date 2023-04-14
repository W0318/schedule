package com.schedule.service;

import com.schedule.entity.Period;
import com.schedule.mapper.PeriodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    @Autowired
    PeriodMapper periodMapper;

    public List<Period> getGeneralPeriodAll() {
        return periodMapper.getGeneralPeriodAll();
    }

    public List<Period> getStorePeriodAll(String storeId) {
        return periodMapper.getStorePeriodAll(storeId);
    }

    public List<Period> getGeneralPeriodWeekday() {
        return periodMapper.getGeneralPeriodWeekday();
    }

    public List<Period> getStorePeriodWeekday(String storeId) {
        return periodMapper.getStorePeriodWeekday(storeId);
    }

    public List<Period> getGeneralPeriodWeekend() {
        return periodMapper.getGeneralPeriodWeekend();
    }

    public List<Period> getStorePeriodWeekend(String storeId) {
        return periodMapper.getStorePeriodWeekend(storeId);
    }

    public void insertPeriod(String storeId, String periodName, String type) {
        periodMapper.insertPeriod(storeId, periodName, type);
    }

    public void updatePeriod(int periodId, String periodName) {
        periodMapper.updatePeriod(periodId, periodName);
    }

    public void deletePeriod(int periodId) {
        periodMapper.deletePeriod(periodId);
    }
}
