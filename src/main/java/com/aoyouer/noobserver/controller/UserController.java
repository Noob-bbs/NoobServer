package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.exception.RegisterException;
import com.aoyouer.noobserver.service.UserService;
import com.aoyouer.noobserver.utils.Encrypt;
import com.aoyouer.noobserver.utils.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Resource
    UserService userService;
    //关于用户登录
    @PostMapping(value = "/login")
    public Response login(@RequestBody User user){
        String account = user.getAccount();
        String password = user.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account,password);
        try{
            subject.login(usernamePasswordToken);
            return new Response(200,"登陆成功:" + account);
        }catch (AuthenticationException e){
            return new Response(200,"登陆失败");
        }
    }
    //用户注册
    @PostMapping(value = "/register")
    public Response register(@RequestBody User user){
        try {
            //用账户作为盐
            user.setPassword(Encrypt.encrypt(user.getPassword(),user.getAccount()));
            //添加用户到数据库中，这里可能会抛出异常——账户重名(其实还应该检查一下输入的合法性，虽然前端可以检查，但是有可能被绕过
            user.setSalt(user.getAccount());
            userService.registerUser(user);
            return new Response(200,"已成功注册");
        }catch (RegisterException e){
            return new Response(400,e);
        }
    }
    //登出账号
    @GetMapping(value = "/logout")
    public Response logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Response(200,"成功登出");
    }
}
