package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.DemoUserEntity;
import com.aoyouer.noobserver.repository.DemoUserRepository;
import com.aoyouer.noobserver.service.DemoUserService;
import com.aoyouer.noobserver.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
//下面这个注解可以让controller返回json格式数据
@RestController
@RequestMapping(path = "/api")
public class DemoUserController {
    @Resource
    DemoUserService demoUserService;
    //响应GET方法,注意下面的@RequestParam注解，该请求需要有account=xxx的参数
    @GetMapping(path = "/getdemouser")
    public Response getDemoUser(@RequestParam String account){
        //使用我们在接口中定义的方法
        DemoUserEntity demoUserEntity = demoUserService.getDemoUserByAccount(account);
        //这里我们已经取出了数据表中的对象
        return new Response(200,demoUserEntity);
    }

    //添加用户的方法，这里使用的是GET，之后实际应该要使用POST方法
    @GetMapping(path = "/adddemouser")
    public Response addDemoUser(@RequestParam String account,@RequestParam String password, @RequestParam String email){
        DemoUserEntity demoUserEntity = new DemoUserEntity(account,password,email);
        //save方法不需要我们自己声明，接口中已经有了
        demoUserService.save(demoUserEntity);
        return new Response(200,"成功添加用户");
    }
}
