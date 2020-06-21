package com.aoyouer.noobserver.shiro;

import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getUserAccount() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    //删除缓存
}
