package com.redbee.weatherbee;

import lombok.Data;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Condition {
    //Change to Integer to allow null
    private Integer code;
    private Integer temp;
    private String text;
    private String date;

    private Condition() {}

    public Condition (Integer code, Integer temp, String text, String date) {
        this.code = code;
        this.temp = temp;
        this.text = text;
        this.date = date;
    }
}
