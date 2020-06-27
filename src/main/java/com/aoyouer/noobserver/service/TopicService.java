package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.repository.TopicRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TopicService {
    @Resource
    TopicRepository topicRepository;
}
