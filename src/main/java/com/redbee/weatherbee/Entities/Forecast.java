package com.redbee.weatherbee.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Forecast {
    @Id
    @GeneratedValue
    private Long id;
    private int code;
    private String date;
    private String day;
    private int high;
    private int low;
    private String text;

    private Forecast() {}

    public Forecast(int code, String date, String day, int high, int low, String text) {
        this.code = code;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
    }
}
