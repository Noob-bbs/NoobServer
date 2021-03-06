package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.DemoUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface DemoUserRepository extends CrudRepository<DemoUserEntity,Long> {
    DemoUserEntity findDemoUserEntityByAccount(String account);
}
