package com.aoyouer.noobserver.entitiy;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String avatarUrl;

    //评论只对自己公开
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Post.class)
    private List<Post> postList = new ArrayList<>();
    //topic讨论帖对所有人公开
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Topic.class)
    private List<Topic> topicList = new ArrayList<>();
    //用户所具有的角色

    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Role.class)
    private Set<Role> roleSet;

    public User() {
        //this.roleSet.add(new Role("MEMBER"));
    }

    public User(String account, String password, String email,String nick ,String salt) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.salt = salt;
        this.roleSet = new HashSet<>();
        this.roleSet.add(new Role("MEMBER"));
        this.nick = nick;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void addPost(Post post) {
        postList.add(post);
    }

    public void addTopic(Topic topic) {
        topicList.add(topic);
    }
}
