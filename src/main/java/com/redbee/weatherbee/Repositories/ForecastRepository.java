package com.redbee.weatherbee.Repositories;

import com.redbee.weatherbee.Entities.Forecast;
import org.springframework.data.repository.Repository;

public interface ForecastRepository extends Repository<Forecast, Long> {
    Forecast save(Forecast forecast);
}
