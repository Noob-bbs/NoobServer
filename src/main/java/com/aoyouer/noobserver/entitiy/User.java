package com.aoyouer.noobserver.entitiy;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String account;
    private String nick;
    private String password;
    private String email;
    private String salt;
    //用户所具有的角色

    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Role.class)
    private Set<Role> roleSet;

    public User() {
        //this.roleSet.add(new Role("MEMBER"));
    }

    public User(String account, String password, String email, String salt) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.salt = salt;
        this.roleSet = new HashSet<>();
        this.roleSet.add(new Role("MEMBER"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
