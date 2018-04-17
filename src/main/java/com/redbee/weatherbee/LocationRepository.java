package com.redbee.weatherbee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface LocationRepository extends CrudRepository<Location, Long>{
}
