package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Role;
import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.exception.RegisterException;
import com.aoyouer.noobserver.service.UserService;
import com.aoyouer.noobserver.utils.Encrypt;
import com.aoyouer.noobserver.utils.Response;
import com.sun.xml.bind.v2.TODO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


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
            //关于角色这部分，暂时只作为测试使用
            //TODO 用户默认为普通用户
            if (user.getRoleSet() == null){
                System.out.println("角色集为空");
            }
            else {
                System.out.println("角色集非空" + user.getRoleSet().toString());
            }
            //为了防止构造请求直接修改了权限，所以这里创建一个新对象，只取账户密码邮箱，并设置初始化角色(MEMBER
            User dbUser = new User();
            dbUser.setAccount(user.getAccount());
            dbUser.setPassword(Encrypt.encrypt(user.getPassword(),user.getAccount()));
            dbUser.setSalt(user.getAccount());
            dbUser.setEmail(user.getEmail());
            userService.registerUser(dbUser);
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

    //添加用户
    @PostMapping(value = "/adduser")
    @RequiresRoles(value = {"ADMIN","MANAGER","MEMBER"},logical = Logical.OR)
    public Response addUser(@RequestBody User user){
        userService.registerUser(user);
        return new Response(200,"成功添加用户");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Response authorExceptionHandler(AuthorizationException e){
        return new Response(403,"授权验证失败" + e.getMessage());
    }

}
