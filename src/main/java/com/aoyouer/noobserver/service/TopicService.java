package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class TopicService {
    @Resource
    TopicRepository topicRepository;

    @Transactional
    public Page<Topic> getTopicsByTypeAndPage(int pageSize, int pageNum, String type) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return topicRepository.findAllByType(type, pageable);
    }
}
