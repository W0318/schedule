package com.schedule.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Chromo implements Serializable {
    public ArrayList<Integer> chromo= new ArrayList<>();//需要set染色体长度
    public int len=6;
    public Chromo(){
        Random r = new Random();
        for(int i=0;i<len;i++) {
            chromo.add((Integer) (0));//全都没有，靠突变
        }
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
