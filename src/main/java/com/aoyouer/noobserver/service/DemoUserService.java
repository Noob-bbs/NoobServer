package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.DemoUserEntity;
import com.aoyouer.noobserver.repository.DemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class DemoUserService {

    @Resource
    private DemoUserRepository demoUserRepository;

    @Transactional
    public void save(DemoUserEntity demoUserEntity){
        demoUserRepository.save(demoUserEntity);
    }

    @Transactional
    public DemoUserEntity getDemoUserByAccount(String account){
        return demoUserRepository.findDemoUserEntityByAccount(account);
    }
}
