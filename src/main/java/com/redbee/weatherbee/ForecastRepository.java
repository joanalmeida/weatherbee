package com.redbee.weatherbee;

import org.springframework.data.repository.Repository;

public interface ForecastRepository extends Repository<Forecast, Long> {
    Forecast save(Forecast forecast);
}
