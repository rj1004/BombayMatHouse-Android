package com.rahulcompany.bombaymathouse.Databases.stock;

import com.google.android.material.badge.BadgeDrawable;

import java.util.ArrayList;

public class stockdata {
    private long code;
    private double qty;
    private String item,uom;
    private double profit;

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }


}
