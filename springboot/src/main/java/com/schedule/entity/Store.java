package com.schedule.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Store {
    private String storeId;
    private String storeName;
    private String address;
    private float size;

    private float preTimeNeed = 2;   // 开店前所需时间,最多2小时
    private String preEFormula;   //开店前所需员工公式
    private int preENeed = 1;   //开店前所需员工,最少为1
    private ArrayList<String> prePosLimit;   //开店前员工职位限制
    private float aftTimeNeed = 2;   // 关店后所需时间,最多2小时
    private String aftEFormula;   //关店后所需员工公式
    private int aftENeed = 1;   //关店后所需员工,最少为1
    private ArrayList<String> aftPosLimit;   //关店后员工职位限制
    private String flowFormula;   //客流规则公式
    private int minNeed = 1;   //客流最少所需员工,最少为1
    private ArrayList<String> posLimit;   //日常班次员工职位限制
}
