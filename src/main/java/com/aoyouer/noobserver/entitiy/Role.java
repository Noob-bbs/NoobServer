package com.aoyouer.noobserver.entitiy;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roleName;
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Permission.class)
    private Set<Permission> permissionSet = new HashSet<>();    //不保证顺序

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
        //TODO 如果需要细分权限的话，在这里根据初始化时的roleName设置permissionSet
        this.permissionSet = new HashSet<>(){{
            add(new Permission());
        }};
    }

    public Role(String roleName, Set<Permission> permissionSet) {
        this.roleName = roleName;
        this.permissionSet = permissionSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
