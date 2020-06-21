package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.exception.RegisterException;
import com.aoyouer.noobserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    UserRepository userRepository;
    public User getUserByAccount(String account){
        return userRepository.getUserByAccount(account);
    }
    public void registerUser(User user) throws RegisterException{
        //可以先判断合法性
        //首先要查看是否有同名用户
        User duser = userRepository.getUserByAccount(user.getAccount());
        if (duser != null) throw new RegisterException("用户名已被占用");
        userRepository.save(user);
    }
}