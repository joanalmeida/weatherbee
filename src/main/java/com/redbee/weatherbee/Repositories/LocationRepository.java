package com.redbee.weatherbee.Repositories;

import com.redbee.weatherbee.Entities.Location;
import org.springframework.data.repository.Repository;

import java.util.Set;

public interface LocationRepository extends Repository<Location, Long>, LocationRepositoryCustom {

    Location save(Location location);
    Location findByName(String name);
    Location findById(Long id);
    Set<Location> findAll();
}
