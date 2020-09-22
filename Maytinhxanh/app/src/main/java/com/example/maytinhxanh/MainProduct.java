package com.example.maytinhxanh;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;

public class MainProduct{
    private int Picture;
    private String Name;
    private long Price;
    private float Number;
    private double Total;

    public MainProduct(int picture, String name) {
        Picture = picture;
        Name = name;
    }
    public int getPicture() {
        return Picture;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public Float getNumber() {
        return Number;
    }

    public void setNumber(Float number) {
        Number = number;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

}



