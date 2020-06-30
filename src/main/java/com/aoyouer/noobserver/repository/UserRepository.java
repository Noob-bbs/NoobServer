package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    User getUserByAccount(String account);

    User getUserById(long id);

    List<User> findAll();
}
