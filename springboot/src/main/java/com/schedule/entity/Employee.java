package com.schedule.entity;

import com.alibaba.fastjson.JSONObject;
import com.schedule.algorithm.Chromosome;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Employee {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private String phone;
    private int root;

    private ArrayList<Integer> preferCode;   //偏好数值化0-39
    private ArrayList<ArrayList<Integer>> chromo;
    private int WeekTime = 0;

    /**
     * 将数据库中存储的偏好转换为List<Integer>列表，并数字化
     *
     * @param prefers 数据库偏好列表
     * @return 一位员工的偏好数值
     */
    public void setPreferCode(List<Preference> prefers) {
        System.out.println(prefers);
        ArrayList<Integer> prefer_arr = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            prefer_arr.add(0);
        }
        for (Preference prefer : prefers) {
            switch (prefer.getPreferType()) {
                case "工作日偏好":
                    prefer_arr.set(0, 1);
                    for (int i = 0; i < prefer.getPreferValue().length(); i++) {
                        if (prefer.getPreferValue().charAt(i) == '1') prefer_arr.set(1, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '2') prefer_arr.set(2, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '3') prefer_arr.set(3, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '4') prefer_arr.set(4, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '5') prefer_arr.set(5, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '6') prefer_arr.set(6, 1);//0则check为休息
                        if (prefer.getPreferValue().charAt(i) == '7') prefer_arr.set(7, 1);//0则check为休息
                    }
                    break;
                //工作时间偏好 10:00-21:00(范围)
                case "工作时间偏好":
                    prefer_arr.set(0, 2);
                    char ch1 = prefer.getPreferValue().charAt(0);
                    char ch2 = prefer.getPreferValue().charAt(1);
                    int i = ch1 - '0';
                    int j = ch2 - '0';
                    int res = i * 10 + j;
                    if (prefer.getPreferValue().charAt(3) == '3') {
                        prefer_arr.set((res - 9) * 2 + 8, 1);
                    } else prefer_arr.set((res - 9) * 2 + 7, 1);


                    ch1 = prefer.getPreferValue().charAt(6);
                    ch2 = prefer.getPreferValue().charAt(7);
                    i = ch1 - '0';
                    j = ch2 - '0';
                    res = i * 10 + j;
                    if (prefer.getPreferValue().charAt(9) == '3') prefer_arr.set((res - 9) * 2 + 8, 1);
                    else prefer_arr.set((res - 9) * 2 + 7, 1);

                    for (int k = 8; k <= 31; k++) {
                        if (prefer_arr.get(k) == 1) {
                            while (prefer_arr.get(k) == 1 && k <= 31) k++;
                            while (prefer_arr.get(k) == 0 && k <= 31) {
                                prefer_arr.set(k, 1);
                                k++;
                            }
                            break;
                        }
                    }
                    break;
                case "班次时长偏好":
                    prefer_arr.set(0, 3);
                    JSONObject value = JSONObject.parseObject(prefer.getPreferValue());
                    int day = (int) value.get("day");
//                    int week = (int) value.get("week");

                    prefer_arr.set(32, day);
//                    prefer_arr.set(40, week);
                    break;
                case "无偏好":
                    for (int k = 1; k <= 31; k++) {
                        prefer_arr.set(k, 1);
                    }
            }
        }
        //偏好问题



        System.out.print("该员工偏好数组：");
        for(int i=0;i<33;i++){
            System.out.print(prefer_arr.get(i));
        }
        System.out.println();
        chromo= new ArrayList<>();

        this.preferCode = prefer_arr;
//        return prefer_arr;
    }
    public static HashMap<String,Employee> employee_arrInit() {
        HashMap<String,Employee> retList = new HashMap<>();
        System.out.println("员工HashMap初始化成功！");
        return retList;
    }

//    public void mySetChromo(int day, Chromosome chromosomes) {
//        this.chromo.set(day, chromosomes);
//    }
//    public ArrayList<Chromosome> getChromo() {
//        return this.chromo;
//    }

//    public void reSetChrom(ArrayList<Chromosome> chromo) {
//        this.chromo = chromo;
//    }
}
