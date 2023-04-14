package com.schedule.service;

import com.schedule.entity.Employee;
import com.schedule.beans.Storebean;
import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreMapper storeMapper;

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Store> getAllStore() {
        return storeMapper.getAllStore();
    }

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    public List<Store> getStoreName() {
        return storeMapper.getStoreName();
    }
    public List<Store> getStoreByStoreName(String storeName) {
        return storeMapper.getStoreByStoreName(storeName);
    }
    public List<Store> getStoreByStoreType(String storeType) {
        return storeMapper.getStoreByStoreType(storeType);
    }
    public List<Store> getStore(String storeName, String storeType) {
        return storeMapper.getStore(storeName, storeType);
    }

    //根据员工所在门店Id查找该门店名
    public Store getEmployeeStoreName(String employeeId) {
        return storeMapper.getEmployeeStoreName(employeeId);
    }
    //寻找是否已有该门店id
    public List<Store> getFindIfExistStore(String storeId) {
        return storeMapper.getFindIfExistStore(storeId);
    }

    public void updateStore(String storeId, String storeName, String storeType, String address, Float size, String manger, Integer workers) {
        storeMapper.updateStore(storeId, storeName, storeType, address, size, manger, workers);
    }

    public void updateStoreStateOne(String storeId) {
        storeMapper.updateStoreStateOne(storeId);
    }

    public void updateStoreStateZero(String storeId) {
        storeMapper.updateStoreStateZero(storeId);
    }

    public void deleteStore(String storeId) {
        storeMapper.deleteStore(storeId);
    }

    public void insertStore(String storeId, String storeName, String storeType, String address, Float size, String manger, Integer workers) {
        storeMapper.insertStore(storeId, storeName, storeType, address, size, manger, workers);
    }
    public Store getStoreById(String storeId) {
        return storeMapper.getStoreById(storeId);
    }
    public int getStorePersons(String storeid){
//        System.out.println(storeMapper.getStorePersons(storeid));
        return  storeMapper.getStorePersons(storeid);
    }
    public List<Storebean> getStoresMessage() {
        return storeMapper.getStoresMessage();
    }
}
