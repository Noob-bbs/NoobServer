package com.aoyouer.noobserver.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class Encrypt {
    public static String encrypt(String pwd,String salt){
        //注意这里的加密算法和加密次数要和Shiro配置类里面的对应
        return new SimpleHash("SHA-256",pwd,salt,2).toString();
    }
}
