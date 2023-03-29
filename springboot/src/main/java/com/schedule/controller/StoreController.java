package com.schedule.controller;

import com.schedule.entity.Employee;
import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

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
    @PostMapping("/getStoreEmployee")//获取不同门店的员工们，对数据首页进行渲染
    public List<Employee> getStoreEmployee(@RequestBody Store store){
        System.out.println(store);
        List<Employee> storeemployees = storeMapper.getStoreEmployee(store);
        for(Employee employee:storeemployees){
            System.out.println(employee);
        }
        return storeemployees;
    }
    @GetMapping("/test")
    public void test(){
        new Store().use_GA(1);
    }
}
