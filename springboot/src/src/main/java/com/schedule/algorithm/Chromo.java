package com.schedule.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromo {
    List<Integer> chromosome = new ArrayList<>();
    int len;   //染色体长度
    int fitness = 0;   //适应度
    double p_select = 0;   //选择概率
    double p_expect = 0;   //期望概率
    int isSelected = 0;   //是否被选择

    Chromo(int len) {
        this.len = len;
        for (int i = 0; i < len; i++) {
            chromosome.add((new Random()).nextInt(2));
        }
    }
}
