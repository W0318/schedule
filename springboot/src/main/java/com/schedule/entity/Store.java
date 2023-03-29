package com.schedule.entity;

import com.schedule.config.Chromo;
import com.schedule.config.GA_group;
import com.schedule.controller.EmployeeController;
import com.schedule.mapper.EmployeeMapper;
import com.schedule.mapper.StoreMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.Style;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class Store implements Serializable {
    private String storeId;
    private String storeName;//店名
    private String address;//地址
    private double size;//占地面积
    private String manger;//负责人
    private int workers;//员工数量
    private HashMap<String, Employee> employees;//员工集


    public Store() {
        this.storeName = "";
        this.address = "";
        this.size =0;
        this.employees = new HashMap<>();

        int pre = (int) (preWorkTime / 0.5);
        int later = (int) (aftWorkTime / 0.5);
        int len = pre + later + 24;

        ArrayList<Chromo> chro = new ArrayList<>();
        for (Employee stu : employees.values()) {
            chro.add(stu.chro);
        }
        ga_group = new GA_group(chro);


        ga_group.setLen(len);
        ga_group.setSize(employees.size());
    }
    public Store(String name, String address, double size, HashMap<String, Employee> employees) {
        this.storeName = name;
        this.address = address;
        this.size = size;
        this.employees = employees;

        int pre = (int) (preWorkTime / 0.5);
        int later = (int) (aftWorkTime / 0.5);
//        int len = pre + later + 24;
        int len = 6;

        ArrayList<Chromo> chro = new ArrayList<>();
        for (Employee stu : employees.values()) {
            chro.add(stu.chro);
        }
        ga_group = new GA_group(chro);


        ga_group.setLen(len);
        ga_group.setSize(employees.size());
    }


    public static HashMap<String,Employee> employee_arrInit() {
        HashMap<String,Employee> retList = new HashMap<>();
        System.out.println("员工HashMap初始化成功！");
        return retList;
    }

    //预计客流模型
    private ArrayList<Double> PreModle;
    ArrayList<Chromo> group;
    ///////////////////////////////////////////////////////
    private double preWorkTime = 0.5;//0.5的倍数，开门前preWorkTime小时  a
    private int StoreSizeNeedBefore = 50;// b
    private double passengerFlowNeed = 3.8;//客流passengerFlowNeed  c
    private int free_population = 1;//没人的时候free_population值班  d
    private double aftWorkTime = 1.0;//0.5的倍数，关门后aftWork小时  e
    private int StoreSizeNeedAfter1 = 50;//f
    private int StoreSizeNeedAfter2 = 2;//g
    //////////////////////////////////////////////////////
    private GA_group ga_group;
    @Autowired
    private EmployeeMapper employeeMapper;

    public void use_GA(int Day){
//        int pre = (int) (preWorkTime / 0.5);
//        int later = (int) (aftWorkTime / 0.5);
//        int len = pre + later + 24;
        int len = 6;
        for (Employee employee : employees.values()) {
            Employee tmp = new Employee();
            tmp = employee;
            tmp.chro.len = len;
            employees.replace(employee.getEmployeeName(), tmp);
        }//设置长度
        for (Employee employee : employees.values()) {
            Employee tmp = new Employee();
            tmp = employee;
            Chromo chro = new Chromo();
            tmp.chro = chro;
            employees.replace(employee.getEmployeeName(), tmp);
        }

        PreModle = new ArrayList<>();
        PreModle.add(13.4);
        PreModle.add(26.9);
        PreModle.add(29.3);
        PreModle.add(17.2);
        PreModle.add(11.6);
        PreModle.add(11.6);

//        PreModle.add(0.0);
//        PreModle.add(1.3);
//        PreModle.add(5.7);
//        PreModle.add(11.1);
//        PreModle.add(13.4);
//        PreModle.add(13.3);
//        PreModle.add(17.3);
//        PreModle.add(22.8);
//        PreModle.add(26.9);
//        PreModle.add(21.6);
//        PreModle.add(23.4);
//        PreModle.add(28.7);
//        PreModle.add(29.3);
//        PreModle.add(26.4);
//        PreModle.add(22.1);
//        PreModle.add(17.2);
//        PreModle.add(15.2);
//        PreModle.add(13.3);
//        PreModle.add(11.6);
//        PreModle.add(7.2);
//        PreModle.add(5.6);
//        PreModle.add(2.1);
//        PreModle.add(1.3);
//        PreModle.add(1.0);


        int PreNum = (int) Math.abs((size / StoreSizeNeedBefore));
        int FreeNum = (int) (free_population);
        int AftNum = ((int) (size / StoreSizeNeedAfter1) + StoreSizeNeedAfter2) + 1;

        ArrayList<Double> PassFlowNum = new ArrayList<>();
        for (double tmp : PreModle) {
//            PassFlowNum.add(String.format("%.1f",(tmp) / passengerFlowNeed));
            PassFlowNum.add((tmp) / passengerFlowNeed);
        }



        ArrayList<ArrayList<Integer>> loves_arr=new ArrayList<>();
        for(Employee stu:employees.values()) {
            loves_arr.add(stu.love_arr);
        }
//        group = ga_group.GA(PreNum, FreeNum, AftNum, PassFlowNum, pre, later,Day,loves_arr);
        group = ga_group.GA(PreNum, FreeNum, AftNum, PassFlowNum,Day,loves_arr);
        /**
         * 需要记录员工的偏好集合，传入并check，还有星期几，也就是day
         */
        System.out.println(PassFlowNum.size());
        System.out.println(PreNum);
        System.out.println(FreeNum);
        System.out.println(AftNum);
        System.out.println(PreModle);
        System.out.println(PassFlowNum);
        System.out.println(group);
//        for(Employee employee:employeeMapper.queryEmployeeByStoreId("1")){
//            System.out.println(employee);
//        }
//        System.exit(1);//24
    }
}
