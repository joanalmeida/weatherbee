package com.redbee.weatherbee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface LocationRepository extends Repository<Location, Long> {

    Location save(Location location);
}
