package com.aoyouer.noobserver.entitiy;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(columnDefinition="LONGTEXT")
    private String content;

    private long time;

    private int likeNum;

    private long userId;

    private String account;

    private String nick;

    private String avatarUrl;

    private long topicId;

    public Post() {
    }

    public Post(String content, long time, int likeNum, long userId
            , String account, String nick, String avatarUrl, long topicId) {
        this.content = content;
        this.likeNum = likeNum;
        this.time = time;
        this.userId = userId;
        this.account = account;
        this.nick = nick;
        this.avatarUrl = avatarUrl;
        this.topicId = topicId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }
}
