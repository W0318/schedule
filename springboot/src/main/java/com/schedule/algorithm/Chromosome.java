package com.schedule.algorithm;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Chromosome {
    public ArrayList<Integer> chromosome = new ArrayList<>();
    private int len;

    public Chromosome(int len) {
        this.len = len;
        for (int i = 0; i < len; i++) {  //染色体二进制编码
            chromosome.add(0);  //全都没有，靠突变
        }
    }
}
