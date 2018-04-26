package com.redbee.weatherbee.Repositories;

import com.redbee.weatherbee.Entities.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);
    User findByEmailAndPassword(String email, String password);
    User findById(Long id);
    User findByEmail(String email);
}
