package com.schedule.algorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Chromosome {
    public ArrayList<Integer> chromosome = new ArrayList<>();
    private int len;

    public Chromosome() {
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            chromosome.add(0);//全都没有，靠突变
        }
    }

    public Chromosome(int len) {
        this.len = len;
        for (int i = 0; i < len; i++) {  //染色体二进制编码
            chromosome.add(0);  //全都没有，靠突变
        }
    }

}
