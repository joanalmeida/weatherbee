package com.redbee.weatherbee;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);
    User findByEmailAndPassword(String email, String password);
}
