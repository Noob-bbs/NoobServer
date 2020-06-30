package com.aoyouer.noobserver.entitiy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Topic {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private String title;
    private String content;
    private String type;        //板块类型

    @ElementCollection
    private Set<String> tags;
    //评论数量为list的数量，内容为list第一楼的内容,一切皆为第一楼的（包括User）
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(targetEntity = Post.class)
    private List<Post> postList = new ArrayList<>();

    public Topic() {
    }

    public Topic(String title, String type, Set<String> tags, Long userId, String content) {
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.tags = new HashSet<>();
        this.tags.addAll(tags);
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent(){
        return content;
    }

        public void setContent(String content){
            this.content = content;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return postList.get(0).getUser();
    }



    public void addPost(Post post) {
        postList.add(post);
    }


}
