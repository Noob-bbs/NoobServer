package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {
    @Resource
    TopicRepository topicRepository;

    @Transactional
    public Page<Topic> getTopicsByTypeAndPage(int pageSize, int pageNum, String type) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("updateTime").descending());
        return topicRepository.findAllByType(type, pageable);
    }

    @Transactional
    public Page<Topic> getTopicsByPage(int pageSize, int pageNum) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return topicRepository.findAll(pageable);
    }

    public Map<String, Integer> getTagsInfo() {
        Map<String, Integer> tagsInfo = new HashMap<>();
        for (Topic topic :
                topicRepository.findAll()) {
            for (String s :
                    topic.getTags()) {
                for (String exist :
                        tagsInfo.keySet()) {
                    if (s.equals(exist)) {  //存在就加上1，否则就新建一个。
                        tagsInfo.put(s, tagsInfo.get(s) + 1);
                    } else {
                        tagsInfo.put(s, 1);
                    }
                }
            }
        }
        return tagsInfo;
    }

    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public int getTopicListSize() {
        return topicRepository.findAll().size();
    }

    public Topic getTopicById(long id) {
        return topicRepository.getTopicById(id);
    }

    public List<Topic> getAllByType(String type) {
        return topicRepository.findAllByType(type);
    }

    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    public void deleteById(long id) throws Exception{
        topicRepository.deleteById(id);
    }
}
