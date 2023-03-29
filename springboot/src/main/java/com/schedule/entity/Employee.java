package com.schedule.entity;

import com.schedule.config.Chromo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Employee implements Serializable {
    private String employeeId;
    private String storeId;
    private String employeeName;
    private String email;
    private String position;
    private String phone;
    private int root;


    //////////////////////////////////////////////////////////
    public String loves;//偏好 三种①工作日  ②工作时间范围 如9-18 ③日工作时间 ④无，正常工作
    public Chromo chro;//暂时用一天的时间集染色体来进行GA
    public ArrayList<Integer> love_arr=new ArrayList<>();

    public Employee() {
        this.employeeName = "";
        this.position = "";
        this.phone = "";
        this.email = "";
        this.storeId = "";
        this.loves = "";
        chro=new Chromo();

    }

    public Employee(String name, String pos, String tel, String mail, String stores, String loves) {
        this.employeeName = name;
        this.position = pos;
        this.phone = tel;
        this.email = mail;
        this.storeId = stores;
        this.loves = loves;
        /**
         * 工作日偏好 1、3、4、5、7
         * 工作时间偏好 9:30-21:30 9:00-21:00
         * 日工作时间偏好 6小时
         * 无偏好，正常工作
         *
         * 解析loves为love_arr
         * 0设置种类(1 2 3) 1-7 工作日   8-31 工作时间（工作日代表9-21 周末代表10-22）   32 日工作时间偏好
         */
        for (int i = 0; i < 33; i++) {
            love_arr.add(0);
        }

        //偏好问题
//        if(loves.charAt(2)=='日'){
//            love_arr.set(0,1);
//            for(int i=6;i<loves.length();i++) {
//                if(loves.charAt(i)=='1') love_arr.set(1,1);//0则check为休息
//                if(loves.charAt(i)=='2') love_arr.set(2,1);//0则check为休息
//                if(loves.charAt(i)=='3') love_arr.set(3,1);//0则check为休息
//                if(loves.charAt(i)=='4') love_arr.set(4,1);//0则check为休息
//                if(loves.charAt(i)=='5') love_arr.set(5,1);//0则check为休息
//                if(loves.charAt(i)=='6') love_arr.set(6,1);//0则check为休息
//                if(loves.charAt(i)=='7') love_arr.set(7,1);//0则check为休息
//            }
//        }
//        //工作时间偏好 10:00-21:00(范围)
//        else if(loves.charAt(2)=='时') {
//            love_arr.set(0,2);
//            char ch1 = loves.charAt(7);
//            char ch2 = loves.charAt(8);
//            int i = ch1-'0';
//            int j = ch2-'0';
//            int res = i*10+j;
//            if(loves.charAt(10)=='3') {
//                love_arr.set((res - 9) * 2 + 8 , 1);
//                System.out.println();
//            }
//            else love_arr.set((res-9)*2+7,1);
//
//
//            ch1 = loves.charAt(13);
//            ch2 = loves.charAt(14);
//            i = ch1-'0';
//            j = ch2-'0';
//            res = i*10+j;
//            if(loves.charAt(16)=='3') love_arr.set((res-9)*2+8,1);
//            else love_arr.set((res-9)*2+7,1);
//
//            for(int k=8;k<=31;k++)
//            {
//                if(love_arr.get(k)==1)
//                {
//                    while(love_arr.get(k)==1&&k<=31) k++;
//                    while(love_arr.get(k)==0&&k<=31) {
//                        love_arr.set(k,1);
//                        k++;
//                    }
//                    break;
//                }
//            }
//        }
//        else if(loves.charAt(2)=='作') {
//            love_arr.set(0,3);
//            int z = loves.charAt(8)-'0';//不能超过8小时
//            love_arr.set(32,z*2);
//        }


        System.out.print("该员工偏好数组：");
        for(int i=0;i<33;i++){
            System.out.print(love_arr.get(i));
        }
        System.out.println();
        chro=new Chromo();
    }

//    public Employee(String 李四, String 经理, String s, String s1, String s2, String s3) {
//    }

    public static HashMap<String,Employee> employee_arrInit() {
        HashMap<String,Employee> retList = new HashMap<>();
        System.out.println("员工HashMap初始化成功！");
        return retList;
    }

}
