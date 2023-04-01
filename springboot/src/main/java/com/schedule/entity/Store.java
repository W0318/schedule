package com.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {
    private String storeId;
    private String storeName;
    private String address;
    private float size;
    private String manger;
    private int workers;
}
