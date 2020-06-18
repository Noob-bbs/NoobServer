package com.aoyouer.noobserver.shiro;

import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class NoobRealm extends AuthorizingRealm {
    @Resource
    UserService userService;

    //需要重写（实现）授权信息获取方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }
    //根据token中的用户名从数据库中获取密码和盐
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userAccount = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByAccount(userAccount);
        //获取数据库中的密码（hash加密后）
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        //SimpleAuthenticationInfo中的salt使用的是byte[]而我们储存的是字符串
        return new SimpleAuthenticationInfo(userAccount,passwordInDB, ByteSource.Util.bytes(salt),getName());
    }
}
