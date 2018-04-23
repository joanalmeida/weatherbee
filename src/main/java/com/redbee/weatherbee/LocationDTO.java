package com.redbee.weatherbee;

public class LocationDTO {
    //Add public to allow Jackson serialization
    public Long id;
    public String name;

    public LocationDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
