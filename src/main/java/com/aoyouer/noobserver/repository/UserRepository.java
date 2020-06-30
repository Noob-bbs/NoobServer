package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User getUserByAccount(String account);

    User getUserById(long id);
}
