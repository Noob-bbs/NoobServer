package com.aoyouer.noobserver.entitiy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TopicPage {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int pageNum;    //从0开始计算，在第几页。请前端记得装换，前端需要换做1开始

    private int pageSize;   //大小，一页有多少个topic

    private int lastPage;   //从1开始计算,页总数：listSize/pageSize;

    @OneToMany(targetEntity = Topic.class)
    private List<Topic> topicList;

    public TopicPage(int pageNum, int pageSize, int lastPage, List<Topic> topicList) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.topicList = topicList;
        this.lastPage = lastPage;
    }

    public TopicPage() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
