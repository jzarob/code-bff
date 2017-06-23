package com.e451.rest.repository;

import com.e451.rest.domains.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by j747951 on 6/22/2017.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
