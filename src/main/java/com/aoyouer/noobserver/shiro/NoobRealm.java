package com.aoyouer.noobserver.shiro;

import com.aoyouer.noobserver.entitiy.Permission;
import com.aoyouer.noobserver.entitiy.Role;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class NoobRealm extends AuthorizingRealm {
    @Resource
    UserService userService;
    Logger logger = LoggerFactory.getLogger("RealmLogger");
    //需要重写（实现）授权信息获取方法
    //授权管理，登录用户的权限管理
    /**
     * https://zhuanlan.zhihu.com/p/98365213
     * 授权权限
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println(principalCollection);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //??? 可以这样取出当前会话的用户对象吗？
        logger.info("principalCollection.getPrimaryPrincipal()" + principalCollection.toString());
        User user = userService.getUserByAccount(principalCollection.getPrimaryPrincipal().toString());
        logger.info("授权中:" + user.getAccount());
        //授权管理
        Set<String> roleNameSet = new HashSet<>();
        Set<String> permissionNameSet = new HashSet<>();
        //从数据库中查询用户
        User dbUser = userService.getUserByAccount(user.getAccount());
        Set<Role> roleSet = dbUser.getRoleSet();
        for (Role role:roleSet){
            roleNameSet.add(role.getRoleName());
            for (Permission permission : role.getPermissionSet()){
                permissionNameSet.add(permission.getPermissionName());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        simpleAuthorizationInfo.setStringPermissions(permissionNameSet);
        simpleAuthorizationInfo.setRoles(roleNameSet);
        return simpleAuthorizationInfo;
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
