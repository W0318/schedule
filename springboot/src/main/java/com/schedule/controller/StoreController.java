package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/store")
public class StoreController  {
    @Autowired
    StoreService storeService;

    /**
     * @author 吴佳丽
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/all")
    public List<Store> getAllStore() {
        return storeService.getAllStore();
    }

    @Autowired
    private StoreMapper storeMapper;
    @GetMapping("/queryStoreList")
    public  List<Store> queryStoreList(){
        List<Store> storeList = storeMapper.queryStoreList();
        for(Store store:storeList){
            System.out.println(store);
        }
        return storeList;
    }

    /**
     * @author 张卓童
     * <p>
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     */
    @GetMapping("/getStoreName")
    public List<Store> getStoreName() {
        return storeService.getStoreName();
    }

    @GetMapping("/getStoreByStoreName/{storeName}")
    public List<Store> getStoreByStoreName(@PathVariable("storeName") String storeName) {
        return storeService.getStoreByStoreName(storeName);
    }

    @GetMapping("/getStoreByStoreType/{storeType}")
    public List<Store> getStoreByStoreType(@PathVariable("storeType") String storeType) {
        return storeService.getStoreByStoreType(storeType);
    }

    @GetMapping("/getStore/{storeName}/{storeType}")
    public List<Store> getStoreByStore(@PathVariable("storeName") String storeName, @PathVariable("storeType") String storeType) {
        return storeService.getStore(storeName, storeType);
    }

    //根据员工所在门店Id查找该门店名
    @GetMapping("/getEmployeeStoreName/{employeeId}")
    public Store getEmployeeStoreName(@PathVariable("employeeId") String employeeId) {
        return storeService.getEmployeeStoreName(employeeId);
    }

    //寻找是否已有该门店id
    @GetMapping("/getFindIfExistStore/{storeId}")
    public List<Store> getFindIfExistStore(@PathVariable("storeId") String storeId) {
        return storeService.getFindIfExistStore(storeId);
    }

    @PostMapping("/updateStore")
    @ResponseBody
    public void updateStore(@RequestBody Map map) {
        storeService.updateStore(map.get("storeId").toString(), map.get("storeName").toString(), map.get("storeType").toString(), map.get("address").toString(), Float.parseFloat(map.get("size").toString()), map.get("manger").toString(), Integer.parseInt(map.get("workers").toString()));
    }

    @PostMapping("/updateStoreStateOne")
    @ResponseBody
    public void updateStoreStateOne(@RequestBody Map map) {
        storeService.updateStoreStateOne(map.get("storeId").toString());
    }

    @PostMapping("/updateStoreStateZero")
    @ResponseBody
    public void updateStoreStateZero(@RequestBody Map map) {
        storeService.updateStoreStateZero(map.get("storeId").toString());
    }

    @PostMapping("/deleteStore")
    @ResponseBody
    public void deleteStore(@RequestBody Map map) {
        storeService.deleteStore(map.get("storeId").toString());
    }

    @PostMapping("/insertStore")
    @ResponseBody
    public void insertStore(@RequestBody Map map) {
        storeService.insertStore(map.get("storeId").toString(), map.get("storeName").toString(), map.get("storeType").toString(), map.get("address").toString(), Float.parseFloat(map.get("size").toString()), map.get("manger").toString(), Integer.parseInt(map.get("workers").toString()));
    }
}
